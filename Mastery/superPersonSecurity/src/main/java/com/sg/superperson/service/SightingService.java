/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.service;

import com.sg.superperson.dto.Sighting;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author seanking
 */
public interface SightingService {
    public Sighting addSighting(Sighting sighting);
    public Sighting getSighting(int sightingId);
    public List<Sighting> getAllSightings();
    public List<Sighting> getAllSightingByDate(LocalDate date);
    public void changeSighting(Sighting sighting);
    public void deleteSighting(int sightingId);
    
}
