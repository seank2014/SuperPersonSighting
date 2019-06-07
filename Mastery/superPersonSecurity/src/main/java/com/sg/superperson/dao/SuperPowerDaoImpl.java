/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.dao;

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
public class SuperPowerDaoImpl implements SuperPowerDao {
    
@Autowired
JdbcTemplate jdbc;

    private final String addSuperPower = "INSERT INTO SuperPower(Name, Description) VALUES(?,?);";
    private final String getSuperPower = "SELECT * FROM SuperPower WHERE SuperPowerId = ?";
    private final String getAllSuperPowers = "SELECT * FROM SuperPower";
    private final String editPowers = "UPDATE SuperPower SET Name = ?, Description = ? WHERE SuperPowerId = ?";
    
    @Override
    @Transactional
    public SuperPower addSuperPower(SuperPower superPower) {
        jdbc.update(addSuperPower,
                superPower.getName(),
                superPower.getDescription());
        int newId = jdbc.queryForObject("Select LAST_INSERT_ID()", Integer.class);
        superPower.setSuperPowerId(newId);
        return superPower;
    }

    @Override
    public SuperPower getSuperPower(int superPowerId) {
           try{
        return jdbc.queryForObject(getSuperPower, new PowerMapper(), superPowerId);
           }catch(DataAccessException ex){
               return null;
           }  
        
    }

    @Override
    public List<SuperPower> getAllSuperPowers() {
        return jdbc.query(getAllSuperPowers, new PowerMapper());
    }
    
     @Override
    public void editPowers(SuperPower superpower) {
        jdbc.update(editPowers,
                superpower.getName(),
                superpower.getDescription(),
                superpower.getSuperPowerId());
    }

    @Override
    @Transactional
    public void deleteSuperPower(int superPowerId) {
        final String SuperPerson_Organization= "DELETE so.* FROM SuperPersonOrg so JOIN "
                + "SuperPerson sp ON so.SuperPersonId = sp.SuperPersonId WHERE sp.SuperPowerId = ?";
        
                jdbc.update(SuperPerson_Organization, superPowerId);

        final String otherDeleteconnect = "DELETE ss.* FROM SuperPersonSighting ss "
                + "JOIN SuperPerson sp ON ss.SuperPersonId = sp.SuperPersonId WHERE sp.SuperPowerId = ?";
        
        jdbc.update(otherDeleteconnect, superPowerId);
        
        
        final String deleteSuper = "DELETE FROM SuperPerson WHERE SuperPowerId = ?";
                jdbc.update(deleteSuper, superPowerId);
                
     final String deleteSuperPower = "DELETE FROM SuperPower WHERE SuperPowerId = ?";

        jdbc.update(deleteSuperPower, superPowerId);

    }

   
   
    
    final public static class PowerMapper implements RowMapper<SuperPower>{

        @Override
        public SuperPower mapRow(ResultSet rs, int index) throws SQLException {
            SuperPower superPower = new SuperPower();
            superPower.setSuperPowerId(rs.getInt("SuperPowerId"));
            superPower.setName(rs.getString("Name"));
            superPower.setDescription(rs.getString("Description"));
            return superPower;
        }
    }
    }
