/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.dao;

import com.sg.superperson.dto.User;
import java.util.List;

/**
 *
 * @author seanking
 */
public interface UserDao {   
    User getUserById(int id);
    User getUserByUsername(String username);
    List<User>getAllUsers();
    void updateUser(User user);
    void deleteUser(int id);
    User createUser(User user);
    
}
