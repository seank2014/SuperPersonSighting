/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.service;

import com.sg.superperson.dao.LocationDao;
import com.sg.superperson.dto.Location;
import com.sg.superperson.dto.SuperPerson;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author seanking
 */
@Service
public class LocationServiceImpl implements LocationService {
    
    private LocationDao loc;
    
    public LocationServiceImpl(LocationDao loc){
        this.loc = loc;
    }

    @Override
    public Location createLocation(Location location) {
        return loc.createLocation(location);
    }

    @Override
    public Location getLocation(int locationId) {
        return loc.getLocation(locationId);

    }

    @Override
    public List<Location> getAllLocations() {
        return loc.getAllLocations();
    }

    @Override
    public List<Location> getAllLocationsByHero(SuperPerson superson) {
        return loc.getAllLocationsByHero(superson);
    }

    @Override
    public void changeLocation(Location location) {
        loc.changeLocation(location);
    }

    @Override
    public void deleteLocation(int locationId) {
        loc.deleteLocation(locationId);
    }

   
    
}
