/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.controllers;

import com.sg.superperson.dto.Location;
import com.sg.superperson.dto.Sighting;
import com.sg.superperson.dto.SuperPerson;
import com.sg.superperson.service.LocationService;
import com.sg.superperson.service.SightingService;
import com.sg.superperson.service.SuperPersonService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author seanking
 */
@Controller
public class SightingController {

    @Autowired
    LocationService locationservice;

    @Autowired
    SightingService sightingservice;

    @Autowired
    SuperPersonService superpersonservice;

    @GetMapping("sightings")
    public String displaySighting(Model model) {
        List<Sighting> sightings = sightingservice.getAllSightings();
        List<Location> locations = locationservice.getAllLocations();
        List<SuperPerson> superPersons = superpersonservice.getAllSupers();
        model.addAttribute("sightings", sightings);
        model.addAttribute("locations", locations);
        model.addAttribute("superPersons", superPersons);

        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(@Valid Sighting sighting, BindingResult result,
        HttpServletRequest request, Model model) {
        String locationId = request.getParameter("LocationId");
        String[] superPersonIds = request.getParameterValues("SuperPersonId");

        sighting.setLocation(locationservice.getLocation(Integer.parseInt(locationId)));

        List<SuperPerson> superPersons = new ArrayList<>();
        if (superPersonIds != null) {
            for (String superPersonId : superPersonIds) {
                superPersons.add(superpersonservice.getSuperPerson(Integer.parseInt(superPersonId)));
            }
        } else {
            FieldError error = new FieldError("sighting", "superPersons", "Must include one SuperPerson");
            result.addError(error);

        }
        sighting.setSuperPersons(superPersons);

        if (result.hasErrors()) {
            model.addAttribute("locations", locationservice.getAllLocations());
            model.addAttribute("superPersons", superpersonservice.getAllSupers());
            model.addAttribute("sighting", sighting);
            return "addSighting";
        }
        sightingservice.addSighting(sighting);

        return "redirect:/sightings";
    }

    @GetMapping("sightingDetail")
    public String sightingDetail(Integer SightingId, Model model) {
        Sighting sighting = sightingservice.getSighting(SightingId);
        model.addAttribute("sighting", sighting);
        return "sightingDetail";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(Integer SightingId) {
        sightingservice.deleteSighting(SightingId);;
        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(Integer SightingId, Model model) {
        Sighting sighting = sightingservice.getSighting(SightingId);
        List<Location> locations = locationservice.getAllLocations();
        List<SuperPerson> superPersons = superpersonservice.getAllSupers();
        model.addAttribute("sighting", sighting);
        model.addAttribute("locations", locations);
        model.addAttribute("superPersons", superPersons);
        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(@Valid Sighting sighting, BindingResult result, HttpServletRequest request,
            Model model) {
        String locationId = request.getParameter("LocationId");
        String[] superPersonIds = request.getParameterValues("SuperPersonId");

        sighting.setLocation(locationservice.getLocation(Integer.parseInt(locationId)));

        List<SuperPerson> superPersons = new ArrayList<>();
        if (superPersonIds != null) {
            for (String superPersonId : superPersonIds) {
                superPersons.add(superpersonservice.getSuperPerson(Integer.parseInt(superPersonId)));
            }
        } else {
            FieldError error = new FieldError("sighting", "superPersons", "Must include one SuperPerson");
            result.addError(error);
        }

        sighting.setSuperPersons(superPersons);

        if (result.hasErrors()) {
            model.addAttribute("locations", locationservice.getAllLocations());
            model.addAttribute("superPersons", superpersonservice.getAllSupers());
            model.addAttribute("sighting", sighting);
            return "editSighting";
        }
        sightingservice.changeSighting(sighting);

        return "redirect:/sightings";

    }

}
