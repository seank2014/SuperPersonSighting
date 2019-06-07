/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.service;

import com.sg.superperson.dao.SightingDao;
import com.sg.superperson.dao.SuperPersonDao;
import com.sg.superperson.dto.Sighting;
import com.sg.superperson.dto.SuperPerson;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SightingServiceImpl implements SightingService {
    
    private SightingDao sight;
    private SuperPersonDao superperson;
    
    public SightingServiceImpl(SightingDao sight, SuperPersonDao superperson){
        this.sight = sight;
        this.superperson = superperson;
    }
    

    @Override
    public Sighting addSighting(Sighting sighting) {
        return sight.addSighting(sighting);
    }

    @Override
    public Sighting getSighting(int sightingId) {
return sight.getSighting(sightingId);
    }

    @Override
    public List<Sighting> getAllSightings() {
        return sight.getAllSightings();
    }

    @Override
    public List<Sighting> getAllSightingByDate(LocalDate date) {
        return sight.getAllSightingByDate(date);
    }

    @Override
    public void changeSighting(Sighting sighting) {
        sight.changeSighting(sighting);
    }

    @Override
    public void deleteSighting(int sightingId) {
        sight.deleteSighting(sightingId);
    }

    
    
}
