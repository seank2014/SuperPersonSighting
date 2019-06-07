/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.dao;
import com.sg.superperson.dto.SuperPerson;
import com.sg.superperson.dto.Sighting;

/**
 *
 * @author seanking
 */
public interface SuperPersonSightingDao {

    //create
    public void createSuperPersonSighting(Sighting sighting, SuperPerson superPerson);
    //delete  
    public void deleteSuperPersonSighting(int sightingId, int superPersonId);

}
