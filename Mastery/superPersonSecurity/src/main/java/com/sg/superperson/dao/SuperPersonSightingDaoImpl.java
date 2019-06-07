/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.dao;

import com.sg.superperson.dto.Sighting;
import com.sg.superperson.dto.SuperPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SuperPersonSightingDaoImpl implements SuperPersonSightingDao {

    @Autowired
    JdbcTemplate jdbc;
    
    final String createSuperPersonSighting = "INSERT INTO SuperPersonSighting(SightingId, SuperPersonId) VALUES (?,?)";
    final String deleteSuperPersonSighting = "DELETE FROM SuperPersonSighting WHERE SightingId = ? AND SuperPersonId = ?;";
  

     @Override
     @Transactional
    public void createSuperPersonSighting(Sighting sighting, SuperPerson superPerson) {
            jdbc.update(createSuperPersonSighting,
                    sighting.getSightingId(),
                    superPerson.getSuperPersonId());
    }

    @Override
    public void deleteSuperPersonSighting(int sightingId, int superPersonId) {
        jdbc.update(deleteSuperPersonSighting, superPersonId, sightingId);
    }
}
