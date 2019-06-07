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
import java.util.List;
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
public class AdminController {
    
        Set<ConstraintViolation<User>> violations = new HashSet<>();

    
    @Autowired
    UserDao users;
    
    @Autowired
    RoleDao roles;
    
    @Autowired
    PasswordEncoder encoder;
   
    
    
    @GetMapping("/admin")
    public String displayAdminPage(Model model){
        model.addAttribute("users", users.getAllUsers());
        model.addAttribute("errors", violations);
        return "admin";
    }
    
    @PostMapping("/addSideKick")
    public String addSideKick(String username, String password){
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
     
      return "redirect:/admin";
    }
    
    @PostMapping("/deleteUser")
    public String deleteUser(Integer id){
        users.deleteUser(id);
        return "redirect:/admin";
    }
    
    @GetMapping("/editUser")
    public String editUserDisplay(Model model, Integer id, Integer error){
        User user = users.getUserById(id);
        List<Role> roleList = roles.getAllRoles();
        
        model.addAttribute("user", user);
        model.addAttribute("roles", roleList);
        
        if(error != null){
            if(error == 1){
                model.addAttribute("error", "Passwords did not match, password was not updated.");
        }
        }
        return "editUser";
    }
    
    @PostMapping(value="/editUser")
    public String editUserAction(String[] roleIdList, Boolean enabled, Integer id){
        User user = users.getUserById(id);
        if(enabled !=null){
            user.setEnabled(enabled);
    }else{
            user.setEnabled(false);
        }
        
        Set<Role> roleList = new HashSet<>();
        for(String roleId : roleIdList){
            Role role = roles.getRoleById(Integer.parseInt(roleId));
            roleList.add(role);
        }
        user.setRoles(roleList);
        users.updateUser(user);
        
        return "redirect:/admin";
}
    
    @PostMapping("editPassword")
    public String editPassword(Integer id, String password, String confirmPassword){
        User user = users.getUserById(id);
        
        if(password.equals(confirmPassword)){
            user.setPassword(encoder.encode(password));
            users.updateUser(user);
            return "redirect:/admin";
        }else {
            return "redirect:/editUser?id=" + id + "&error=1";
    }
        
    }
    
  
    
}
