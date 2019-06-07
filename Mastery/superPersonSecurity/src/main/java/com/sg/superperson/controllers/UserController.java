/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.controllers;

import com.sg.superperson.dao.RoleDao;
import com.sg.superperson.dao.UserDao;
import com.sg.superperson.dto.Role;
import com.sg.superperson.dto.User;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author seanking
 */
@Controller
public class UserController {
    
    Set<ConstraintViolation<User>> violations = new HashSet<>();
    
     @Autowired
    UserDao users;
    
    @Autowired
    RoleDao roles;
    
    @Autowired
    PasswordEncoder encoder;
   
    @GetMapping("/signUp")
    public String showSignUpForm(Model model){
        model.addAttribute("errors", violations);
        return "signUp";
    }
    
     @PostMapping("/createSideKick")
    public String createSideKick(String username, String password){
      User user = new User();
      user.setUsername(username);
      user.setPassword(encoder.encode(password));
      user.setEnabled(true);
      
      Set<Role> userRoles = new HashSet<>();
      userRoles.add(roles.getRoleByRole("ROLE_SIDEKICK"));
      user.setRoles(userRoles);
      
      Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
      violations = validate.validate(user);
      
      
      if(violations.isEmpty()){
      users.createUser(user);
      }
      return "redirect:/signUp";
    }
    
}
