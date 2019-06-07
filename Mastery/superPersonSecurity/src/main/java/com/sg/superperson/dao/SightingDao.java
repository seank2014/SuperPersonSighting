/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.dao;

import com.sg.superperson.dto.Sighting;
import com.sg.superperson.dto.SuperPerson;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author seanking
 */
public interface SightingDao {

    //create
    public Sighting addSighting(Sighting sighting);

    //read
    public Sighting getSighting(int sightingId);

    public List<Sighting> getAllSightings();

    public List<Sighting> getAllSightingByDate(LocalDate date);
    
    public List<Sighting> getAllSightingBySuper(SuperPerson superPerson);

    //update
    public void changeSighting(Sighting sighting);

    //delete
    public void deleteSighting(int sightingId);

}
