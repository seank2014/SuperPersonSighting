/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.dao;

import com.sg.superperson.dto.Location;
import com.sg.superperson.dto.SuperPerson;
import java.util.List;

/**
 *
 * @author seanking
 */
public interface LocationDao {

    //Create
    public Location createLocation(Location location);

    //Read
    public Location getLocation(int locationId);

    public List<Location> getAllLocations();

    public List<Location> getAllLocationsByHero(SuperPerson superson); 
    //Update
    public void changeLocation(Location location);

    //Delete
    public void deleteLocation(int locationId);

}
