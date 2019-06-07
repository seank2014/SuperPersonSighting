/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.controllers;

import com.sg.superperson.dto.SuperPower;
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
public class SuperPowerController {
    
    Set<ConstraintViolation<SuperPower>> violations = new HashSet<>();
    
   
   @Autowired
   SuperPowerService superpowerservice;
   
   @GetMapping("superPowers")
   public String displayPowers(Model model){
     List<SuperPower> superPowers = superpowerservice.getAllSuperPowers();
     model.addAttribute("superPowers", superPowers);
     model.addAttribute("errors", violations);
     return "superPowers";
   }
   
   @PostMapping("addPower")
   public String addPower(HttpServletRequest request){
       
       String name = request.getParameter("Name");
       String description = request.getParameter("Description");

       SuperPower superPower = new SuperPower();
       superPower.setName(name);
       superPower.setDescription(description);
       
       Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
       violations = validate.validate(superPower);
       
       if(violations.isEmpty()){
       superpowerservice.addSuperPower(superPower);
       }
       return "redirect:/superPowers";
       
   }
   
   @GetMapping("deleteSuperPower")
   public String deletePower(HttpServletRequest request){
       int id = Integer.parseInt(request.getParameter("SuperPowerId")); //need to use uppercase
       superpowerservice.deleteSuperPower(id);
       
       return "redirect:/superPowers";
}
   
   @GetMapping("editSuperPower")
   public String editSuperPower(HttpServletRequest request, Model model) {
       int id = Integer.parseInt(request.getParameter("SuperPowerId"));
       SuperPower superPower = superpowerservice.getSuperPower(id);
       
       model.addAttribute("superPower", superPower);
       model.addAttribute("errors", violations);
       return "editSuperPower";
   }
   
   @PostMapping("editSuperPower")
   public String performEditSuperPower(HttpServletRequest request){
       int id = Integer.parseInt(request.getParameter("SuperPowerId"));
       SuperPower superPower = superpowerservice.getSuperPower(id);
       
       superPower.setName(request.getParameter("Name"));
       superPower.setDescription(request.getParameter("Description"));
       
       Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
       violations = validate.validate(superPower);
       
       if(violations.isEmpty()){
       superpowerservice.editPowers(superPower);
       }
       return "redirect:/superPowers";
   }
   
}