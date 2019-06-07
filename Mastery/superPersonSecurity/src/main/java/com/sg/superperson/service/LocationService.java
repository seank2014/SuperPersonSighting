/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.service;

import com.sg.superperson.dto.Location;
import com.sg.superperson.dto.Sighting;
import com.sg.superperson.dto.SuperPerson;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author seanking
 */
public interface LocationService {
    
 
    public Location createLocation(Location location);

    public Location getLocation(int locationId);

    public List<Location> getAllLocations();

    public List<Location> getAllLocationsByHero(SuperPerson superson);

    public void changeLocation(Location location);

    public void deleteLocation(int locationId);
    
   
}
