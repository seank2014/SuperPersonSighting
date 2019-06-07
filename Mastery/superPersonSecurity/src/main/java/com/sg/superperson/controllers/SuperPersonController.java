/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.controllers;

import com.sg.superperson.dto.Org;
import com.sg.superperson.dto.SuperPerson;
import com.sg.superperson.dto.SuperPower;
import com.sg.superperson.service.OrganizationService;
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
public class SuperPersonController {

    @Autowired
    OrganizationService organizationservice;

    @Autowired
    SuperPersonService superpersonservice;

    @Autowired
    SuperPowerService superpowerservice;

    @GetMapping("superPersons")
    public String displaySupers(Model model) {
        List<SuperPerson> superPersons = superpersonservice.getAllSupers();
        List<SuperPower> superPowers = superpowerservice.getAllSuperPowers();
        List<Org> orgs = organizationservice.getAllOrganizations();
        model.addAttribute("superPersons", superPersons);
        model.addAttribute("superPowers", superPowers);
        model.addAttribute("orgs", orgs);
        return "superPersons";
    }

    @PostMapping("addSuper")
    public String addSuper(SuperPerson superPerson, HttpServletRequest request) {
        String superPowerId = request.getParameter("SuperPowerId");
        String[] orgIds = request.getParameterValues("OrgId");

        superPerson.setSuperpower(superpowerservice.getSuperPower(Integer.parseInt(superPowerId)));

        List<Org> orgs = new ArrayList<>();
        for (String orgId : orgIds) {
            orgs.add(organizationservice.getOrganization(Integer.parseInt(orgId)));
        }
        superPerson.setOrganizations(orgs);
        superpersonservice.addSuperPerson(superPerson);

        return "redirect:/superPersons";
    }

    @GetMapping("superDetail")
    public String superDetail(Integer SuperPersonId, Model model) {
        SuperPerson superPerson = superpersonservice.getSuperPerson(SuperPersonId);
        model.addAttribute("superPerson", superPerson);
        return "superDetail";
    }

    @GetMapping("deleteSuper")
    public String deleteSuper(Integer SuperPersonId) {
        superpersonservice.deleteSuperPerson(SuperPersonId);
        return "redirect:/superPersons";
    }

    @GetMapping("editSuper")
    public String editSuper(Integer SuperPersonId, Model model) {
        SuperPerson superPerson = superpersonservice.getSuperPerson(SuperPersonId);
        List<Org> orgs = organizationservice.getAllOrganizations();
        List<SuperPower> superPowers = superpowerservice.getAllSuperPowers();
        model.addAttribute("superPerson", superPerson);
        model.addAttribute("orgs", orgs);
        model.addAttribute("superPowers", superPowers);
        return "editSuper";
    }

    @PostMapping("editSuper")
    public String performEditSuper(@Valid SuperPerson superPerson, BindingResult result,
            HttpServletRequest request, Model model) {
        String superPowerId = request.getParameter("SuperPowerId");
        String[] orgIds = request.getParameterValues("OrgId");

        superPerson.setSuperpower(superpowerservice.getSuperPower(Integer.parseInt(superPowerId)));

        List<Org> orgs = new ArrayList<>();
        if (orgIds != null) {
            for (String orgId : orgIds) {
                orgs.add(organizationservice.getOrganization(Integer.parseInt(orgId)));
            }
        } else {
            FieldError error = new FieldError("superPerson", "organizations", "Must include one Organization");
            result.addError(error);
        }
        superPerson.setOrganizations(orgs);

        if (result.hasErrors()) {
            model.addAttribute("superPowers", superpowerservice.getAllSuperPowers());
            model.addAttribute("orgs", organizationservice.getAllOrganizations());
            model.addAttribute("superPerson", superPerson);
            return "editSuper";
        }
        superpersonservice.updateSuperPerson(superPerson);

        return "redirect:/superPersons";
    }

}
