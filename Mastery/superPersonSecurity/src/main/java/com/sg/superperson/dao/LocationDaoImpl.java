/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.dao;

import com.sg.superperson.dto.Location;
import com.sg.superperson.dto.SuperPerson;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class LocationDaoImpl implements LocationDao {

    @Autowired
    JdbcTemplate jdbc;
    private final String createLocation = "INSERT INTO Location (`Name`,`Description`,"
            + " Longitude, Latitude, Street, `City`, ZipCode, `State`) VALUES (?,?,?,?,?,?,?,?);";
    private final String getLocation = "SELECT * FROM Location WHERE LocationId = ?";
    private final String getAllLocations = "SELECT * FROM Location";
    private final String getAllLocationsByHero = "SELECT * "
            + "FROM Location"
            + " INNER JOIN Sighting ON Sighting.LocationId = Location.LocationId "
            + " INNER JOIN SuperPersonSighting ON SuperPersonSighting.SightingId = Sighting.SightingId"
            + " WHERE SuperPersonSighting.SuperPersonId = ?;";
    private final String changeLocation = "Update Location set Name = ?, Description = ?, "
            + " Longitude = ?, Latitude = ?, Street = ?, City = ?, ZipCode =? , State = ? WHERE LocationId = ?";

    @Override
    @Transactional
    public Location createLocation(Location location) {
        jdbc.update(createLocation,
                location.getName(),
                location.getDescription(),
                location.getLongitude(),
                location.getLatitude(),
                location.getStreet(),
                location.getCity(),
                location.getZipcode(),
                location.getState()
        );
        int newId = jdbc.queryForObject("Select LAST_INSERT_ID()", Integer.class);
        location.setLocationId(newId);
        return location;
    }

@Override
    public Location getLocation(int locationId) {
        try {
            return jdbc.queryForObject(getLocation, new LocationMapper(), locationId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        return jdbc.query(getAllLocations, new LocationMapper());
    }

    @Override
    public List<Location> getAllLocationsByHero(SuperPerson superperson) {
        return jdbc.query(getAllLocationsByHero, new LocationMapper(), superperson.getSuperPersonId());
    }

    @Override
    public void changeLocation(Location location) {
        jdbc.update(changeLocation,
                location.getName(),
                location.getDescription(),
                location.getLongitude(),
                location.getLatitude(),
                location.getStreet(),
                location.getCity(),
                location.getZipcode(),
                location.getState(),
                location.getLocationId()
        );
    }

    @Override
    @Transactional
    public void deleteLocation(int locationId) {
        final String SuperPerson_Sighting = "DELETE ss.* FROM SuperPersonSighting ss JOIN "
                + "Sighting s ON ss.SightingId = s.SightingId WHERE s.LocationId = ?";
        jdbc.update(SuperPerson_Sighting, locationId);

        final String deleteSighting = "DELETE FROM Sighting WHERE LocationId = ?";
        jdbc.update(deleteSighting, locationId);

        final String deleteLocationOrg = "DELETE so.* FROM SuperPersonOrg so JOIN"
                + " Org o ON so.OrgId = o.OrgId WHERE o.LocationId = ?";
        jdbc.update(deleteLocationOrg, locationId);

        final String deleteOrganization = "DELETE FROM Org WHERE LocationId = ?";
        jdbc.update(deleteOrganization, locationId);

        final String deleteLocation = "DELETE FROM Location WHERE LocationId = ?";

        jdbc.update(deleteLocation, locationId);
    }

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setLocationId(rs.getInt("LocationId"));
            location.setName(rs.getString("Name"));
            location.setDescription(rs.getString("Description"));
            location.setLongitude(rs.getFloat("Longitude"));
            location.setLatitude(rs.getFloat("Latitude"));
            location.setStreet(rs.getString("Street"));
            location.setCity(rs.getString("City"));
            location.setZipcode(rs.getInt("ZipCode"));
           location.setState(rs.getString("State"));
            return location;

        }
    }

}
