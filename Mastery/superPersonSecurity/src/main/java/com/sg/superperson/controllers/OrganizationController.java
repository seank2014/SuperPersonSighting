/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.controllers;

import com.sg.superperson.dao.SuperPersonOrganizationDao;
import com.sg.superperson.dto.Location;
import com.sg.superperson.dto.Org;
import com.sg.superperson.dto.SuperPerson;
import com.sg.superperson.service.LocationService;
import com.sg.superperson.service.OrganizationService;
import com.sg.superperson.service.SightingService;
import com.sg.superperson.service.SuperPersonService;
import com.sg.superperson.service.SuperPowerService;
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
public class OrganizationController {

    @Autowired
    LocationService locationservice;

    @Autowired
    OrganizationService organizationservice;

    @Autowired
    SightingService sighting;

    @Autowired
    SuperPersonService superpersonservice;

    @Autowired
    SuperPowerService superpower;
    
    @Autowired
    SuperPersonOrganizationDao so;        

    @GetMapping("orgs")
    public String displayOrgs(Model model) {
        List<Org> orgs = organizationservice.getAllOrganizations();
        List<Location> locations = locationservice.getAllLocations();
        List<SuperPerson> superPersons = superpersonservice.getAllSupers();
        model.addAttribute("orgs", orgs);
        model.addAttribute("locations", locations);
        model.addAttribute("superPersons", superPersons);
        return "orgs";
    }

    @PostMapping("addOrgs")
    public String addOrgs(Org org, SuperPerson superPerson, HttpServletRequest request) {
        String locationId = request.getParameter("LocationId");
        String[] superPersonIds = request.getParameterValues("SuperPersonId");

        org.setLocation(locationservice.getLocation(Integer.parseInt(locationId)));
   
        organizationservice.createOrganization(org);
        

        return "redirect:/orgs";
    }

    @GetMapping("deleteOrg")
    public String deleteOrg(Integer OrgId) {
        organizationservice.deleteOrganization(OrgId);
        return "redirect:/orgs";
    }
    
    @GetMapping("editOrg")
    public String editOrg(Integer OrgId, Model model){
        Org org = organizationservice.getOrganization(OrgId);
        List<Location> locations = locationservice.getAllLocations();
        List<SuperPerson> superPersons = superpersonservice.getAllSupers();
        model.addAttribute("org", org);
        model.addAttribute("superPersons", superPersons);
        model.addAttribute("locations", locations);
        return "editOrg";
}
    
    @PostMapping("editOrg")
    public String performEditOrg(Org org, HttpServletRequest request){
    String locationId = request.getParameter("LocationId");
    String[] superPersonIds = request.getParameterValues("SuperPersonId");

    org.setLocation(locationservice.getLocation(Integer.parseInt(locationId)));
                

    organizationservice.editOrganization(org);
                
    return "redirect:/orgs";

}
}
