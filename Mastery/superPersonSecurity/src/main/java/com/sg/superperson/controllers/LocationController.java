/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.controllers;

import com.sg.superperson.dto.Location;
import com.sg.superperson.service.LocationService;
import com.sg.superperson.service.OrganizationService;
import com.sg.superperson.service.SightingService;
import com.sg.superperson.service.SuperPersonService;
import com.sg.superperson.service.SuperPowerService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author seanking
 */
@Controller
public class LocationController {
    
    Set<ConstraintViolation<Location>> violations = new HashSet<>();
    
   @Autowired
   LocationService locationservice;
   
   
 @GetMapping("locations")
   public String displayLocations(Model model){
     List<Location> locations = locationservice.getAllLocations();
     model.addAttribute("locations", locations);
     model.addAttribute("errors", violations);
     return "locations";
     
   }
   
   @PostMapping("addLocation")
   public String addLocation(HttpServletRequest request){
       
     String name = request.getParameter("Name");
     String description = request.getParameter("Description");
     String longitude = request.getParameter("Longitude");
     String latitude  = request.getParameter("Latitude");
     String street = request.getParameter("Street");
     String city = request.getParameter("City");
     String zipcode = request.getParameter("ZipCode");
     String state = request.getParameter("State");

     Location location = new Location();
     location.setName(name);
     location.setDescription(description);
     location.setLongitude(Double.parseDouble(longitude));
     location.setLatitude(Double.parseDouble(latitude));
     location.setStreet(street);
     location.setCity(city);
     location.setZipcode(Integer.parseInt(zipcode));
     location.setState(state);
        
       Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
       violations = validate.validate(location);
        
        if(violations.isEmpty()){
       locationservice.createLocation(location);
       }
        return "redirect:/locations";

   }
   
   @GetMapping("deleteLocation")
   public String deleteLocation(HttpServletRequest request){
       int location = Integer.parseInt(request.getParameter("LocationId"));
       locationservice.deleteLocation(location);
       
       return "redirect:/locations";
   }
   
   @GetMapping("editLocation")
   public String editLocation(HttpServletRequest request, Model model){
       int id = Integer.parseInt(request.getParameter("LocationId"));
       Location location = locationservice.getLocation(id);
       model.addAttribute("location", location);
       model.addAttribute("errors", violations);
       return "editLocation";
   }
   
   @PostMapping("editLocation")
   public String performEditLocation(HttpServletRequest request){
       int id = Integer.parseInt(request.getParameter("LocationId"));
       Location location = locationservice.getLocation(id);
       location.setName(request.getParameter("Name"));
        location.setDescription(request.getParameter("Description"));
        location.setLongitude(Double.parseDouble(request.getParameter("Longitude")));
        location.setLatitude(Double.parseDouble(request.getParameter("Latitude")));
        location.setStreet(request.getParameter("Street"));
        location.setCity(request.getParameter("City"));
        location.setZipcode(Integer.parseInt(request.getParameter("ZipCode")));
        location.setState(request.getParameter("State"));
        
         Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
       violations = validate.validate(location);
        
        if(violations.isEmpty()){
        locationservice.changeLocation(location);
        }
        return "redirect:/locations";
       
   }
   
   
   
    
}
