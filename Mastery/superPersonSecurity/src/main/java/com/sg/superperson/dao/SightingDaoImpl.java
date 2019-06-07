/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.dao;

import com.sg.superperson.dao.LocationDaoImpl.LocationMapper;
import com.sg.superperson.dto.Location;
import com.sg.superperson.dto.Sighting;
import com.sg.superperson.dto.SuperPerson;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SightingDaoImpl implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    SuperPersonDao superPerson;

    final public String addSighting = "INSERT INTO Sighting(Date, Time,LocationId)VALUES(?,?,?);";
    final public String getSighting = "SELECT * FROM Sighting WHERE SightingId = ?";
    final public String getAllSightings = "SELECT * FROM Sighting";
    final public String getAllSightingByDate = "SELECT * FROM Sighting WHERE Date = ? ";
    final public String changeSighting = "Update Sighting set Date = ?, Time = ?, LocationId = ? WHERE SightingId = ?";

    @Override
    public Sighting getSighting(int sightingId) {
        try {
            Sighting sighting = jdbc.queryForObject(getSighting, new SightingMapper(), sightingId);
            sighting.setLocation(getLocationForSighting(sightingId));
            sighting.setSuperPersons(superPerson.getAllSupersBYSighting(sighting));
            return sighting;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
    
     private Location getLocationForSighting(int sightingId) {
        final String getLocationForSight = "SELECT l.* FROM Location l "
                + "JOIN Sighting s ON s.LocationId = l.LocationId WHERE s.SightingId = ? ";
        return jdbc.queryForObject(getLocationForSight, new LocationMapper(), sightingId);
    }
     
      @Override
    public List<Sighting> getAllSightings() {
        List<Sighting> sightings = jdbc.query(getAllSightings, new SightingMapper());
        associateLocationAndSighting(sightings);
        return sightings;
    }
    
 public void associateLocationAndSighting(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setLocation(getLocationForSighting(sighting.getSightingId()));
            sighting.setSuperPersons(superPerson.getAllSupersBYSighting(sighting));
        }
    }
 
  @Override
    public List<Sighting> getAllSightingBySuper(SuperPerson superPerson) {
        final String getAllSupersSighting = "SELECT s.* FROM Sighting s JOIN "
                + "SuperPersonSighting ss ON ss.SightingId = s.SightingId WHERE ss.SuperPersonId = ?";
        List<Sighting> sightings = jdbc.query(getAllSupersSighting, new SightingMapper(), superPerson.getSuperPersonId());
        associateLocationAndSighting(sightings);
        return sightings;
    }

 
    
    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        jdbc.update(addSighting,
                sighting.getDate().toString(),
                sighting.getTime(),
                sighting.getLocation().getLocationId());
        int newId = jdbc.queryForObject("Select LAST_INSERT_ID()", Integer.class);
        sighting.setSightingId(newId);
        insertSuperForSighting(sighting);
        return sighting;
    }

    private void insertSuperForSighting(Sighting sighting) {
        final String insertSuperForSighting = "INSERT INTO "
                + "SuperPersonSighting(SightingId, SuperPersonId) VALUES (?,?)";
        for (SuperPerson superPerson : sighting.getSuperPersons()) {
            jdbc.update(insertSuperForSighting,
                    sighting.getSightingId(),
                    superPerson.getSuperPersonId());
        }
    }

   
    @Override
    public List<Sighting> getAllSightingByDate(LocalDate date) {
        List<Sighting> sightings = jdbc.query(getAllSightingByDate, new SightingMapper(), date.toString());
        associateLocationAndSighting(sightings);
        return sightings;
    }

    @Override
    public void changeSighting(Sighting sighting) {
        jdbc.update(changeSighting,
                sighting.getDate().toString(),
                sighting.getTime(),
                sighting.getLocation().getLocationId(),
                sighting.getSightingId());

        final String deleteSightSuper = "DELETE FROM SuperPersonSighting WHERE SightingId = ?";
        jdbc.update(deleteSightSuper, sighting.getSightingId());
        insertSuperForSighting(sighting);

    }

    @Override
    @Transactional
    public void deleteSighting(int sightingId) {
        final String bridgeSighting = "DELETE FROM SuperPersonSighting WHERE SightingId = ?";
        jdbc.update(bridgeSighting, sightingId);

        final String deleteSighting = "DELETE FROM Sighting WHERE SightingId = ?";

        jdbc.update(deleteSighting, sightingId);
    }

   
    final public static class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setSightingId(rs.getInt("SightingId"));
            sighting.setDate(rs.getDate("Date").toLocalDate());
            sighting.setTime(rs.getTimestamp("Time").toLocalDateTime());
            return sighting;
        }

    }

}
