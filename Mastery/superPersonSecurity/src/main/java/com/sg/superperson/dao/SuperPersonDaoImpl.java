/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.dao;

import com.sg.superperson.dao.SuperPowerDaoImpl.PowerMapper;
import com.sg.superperson.dto.Location;
import com.sg.superperson.dto.Org;
import com.sg.superperson.dto.Sighting;
import com.sg.superperson.dto.SuperPerson;
import com.sg.superperson.dto.SuperPower;
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
public class SuperPersonDaoImpl implements SuperPersonDao {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    OrganizationDao org;
  
    
    private final String addSuperPerson = "INSERT INTO SuperPerson (SuperPowerId, Name, Description, IsVillian)VALUES (?,?,?,?);";
    private final String getSuperPerson = "SELECT * FROM SuperPerson WHERE SuperPersonId = ?";
    private final String getAllSupers = "SELECT * FROM SuperPerson";
    private final String getAllMembers = "SELECT * FROM SuperPerson INNER JOIN SuperPersonOrg ON"
            + " SuperPersonOrg.SuperPersonId = SuperPerson.SuperPersonId WHERE SuperPersonOrg.OrgId = ?";
    private final String getAllSuperPersonByLocation = "SELECT sp.*  FROM SuperPerson sp JOIN "
            + "SuperPersonSighting sps ON sps.SuperPersonId = sp.SuperPersonId JOIN Sighting s "
            + "ON s.SightingId = sps.SightingId WHERE s.LocationId = ?";
    private final String getSuperForSighting = "SELECT * FROM SuperPerson "
                  + "JOIN SuperPersonSighting ON "
                  + "SuperPersonSighting.SuperPersonId = SuperPerson.SuperPersonId "
                  + "WHERE SuperPersonSighting.SightingId = ?";
    private final String getAllLocsOfSuper = "SELECT * "
            + "FROM Location"
            + " INNER JOIN Sighting ON Sighting.LocationId = Location.LocationId "
            + " INNER JOIN SuperPersonSighting ON  SuperPersonSighting.SightingId = Sighting.SightingId "
            + " WHERE SuperPersonSighting.SuperPersonId = ?;";
    private final String updateSuperPerson = "Update SuperPerson set SuperPowerId = ?, Name = ?, Description= ?, IsVillian = ? "
            + "WHERE SuperPersonId = ?";
    private final String deleteSuperPerson = "DELETE FROM SuperPerson WHERE SuperPersonId = ?";

    @Override
    @Transactional
    public SuperPerson addSuperPerson(SuperPerson superPerson) {
        jdbc.update(addSuperPerson,
                superPerson.getSuperpower().getSuperPowerId(),
                superPerson.getName(),
                superPerson.getDescription(),
                superPerson.getIsVillian());
        int newId = jdbc.queryForObject("Select LAST_INSERT_ID()", Integer.class);
        superPerson.setSuperPersonId(newId);
        insertSuperOrg(superPerson);
        return superPerson;

    }

    private void insertSuperOrg(SuperPerson superPerson) {
        final String insertSuperOrg = "INSERT INTO SuperPersonOrg(SuperPersonId, OrgId) VALUES (?,?)";
        for (Org organization : superPerson.getOrganizations()) {          
            jdbc.update(insertSuperOrg,
                    superPerson.getSuperPersonId(),
                    organization.getOrgId());
        }
    }

  
 
    private SuperPower getPowerForSuper(int superPersonId) {
        final String getPowerForSuper = "SELECT p.* FROM SuperPower p "
                + "JOIN SuperPerson sp ON sp.SuperPowerId = p.SuperPowerId WHERE sp.SuperPersonId = ?";
        return jdbc.queryForObject(getPowerForSuper, new PowerMapper(), superPersonId);

    }
    

    @Override
    public SuperPerson getSuperPerson(int superPersonId) {
        try {
            SuperPerson superPerson = jdbc.queryForObject(getSuperPerson, new SuperMapper(), superPersonId);
             superPerson.setSuperpower(getPowerForSuper(superPersonId));
             superPerson.setOrganizations(org.getAllOrganizationsOfASuper(superPerson));
            return superPerson;
        } catch (DataAccessException ex) {
            return null;
        }
    }
    


    @Override
    public List<SuperPerson> getAllSupers() {
        List<SuperPerson> supers = jdbc.query(getAllSupers, new SuperMapper());
        associateAllVaries(supers);
        return supers;
    }

    private void associateAllVaries(List<SuperPerson> supers) {
        for (SuperPerson superPerson : supers) {
            superPerson.setSuperpower(getPowerForSuper(superPerson.getSuperPersonId()));
            superPerson.setOrganizations(org.getAllOrganizationsOfASuper(superPerson)); //maybe I will need to change this to int

        }
        
    }

    @Override
    public List<SuperPerson> getAllMembers(Org organization) {

        List<SuperPerson> supers = jdbc.query(getAllMembers, new SuperMapper(),
                organization.getOrgId());
        associateAllVaries(supers);
        return supers;
    }

    @Override
    public List<SuperPerson> getAllSuperPersonByLocation(Location location) {
        List<SuperPerson> supers = jdbc.query(getAllSuperPersonByLocation, new SuperMapper(),
                location.getLocationId());
        associateAllVaries(supers);
        return supers;

    }
    @Override
     public List<SuperPerson> getAllSupersBYSighting(Sighting sighting){
         List<SuperPerson> supers = jdbc.query(getSuperForSighting, new SuperMapper(), sighting.getSightingId());
          associateAllVaries(supers);
                  return supers;
      }

    @Override
    @Transactional
    public void updateSuperPerson(SuperPerson superPerson) {
        jdbc.update(updateSuperPerson,
                superPerson.getSuperpower().getSuperPowerId(),
                superPerson.getName(),
                superPerson.getDescription(),
                superPerson.getIsVillian(),
                superPerson.getSuperPersonId()
        );

        final String deleteSuperOrg = "DELETE FROM SuperPersonOrg WHERE SuperPersonId = ?";
        jdbc.update(deleteSuperOrg, superPerson.getSuperPersonId());
        insertSuperOrg(superPerson);

        final String deleteSupersight = "DELETE FROM SuperPersonSighting WHERE SuperPersonId = ?";
        jdbc.update(deleteSupersight, superPerson.getSuperPersonId());

    }

    @Override
    @Transactional
    public void deleteSuperPerson(int superPersonId) {
        final String deleteconnect = "DELETE FROM SuperPersonOrg WHERE SuperPersonId = ?";
        jdbc.update(deleteconnect, superPersonId);

        final String otherDeleteconnect = "DELETE FROM SuperPersonSighting WHERE SuperPersonId = ?";
        jdbc.update(otherDeleteconnect, superPersonId);

        jdbc.update(deleteSuperPerson, superPersonId);
    }

    public final static class SuperMapper implements RowMapper<SuperPerson> {

        @Override
        public SuperPerson mapRow(ResultSet rs, int index) throws SQLException {
            SuperPerson sp = new SuperPerson();
            sp.setSuperPersonId(rs.getInt("SuperPersonId"));
            sp.setName(rs.getString("Name"));
            sp.setDescription(rs.getString("Description"));
            sp.setIsVillian(rs.getBoolean("IsVillian"));
            return sp;
        }

    }

}
