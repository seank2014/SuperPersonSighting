/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.dao;

import com.sg.superperson.dao.RoleDaoDB.RoleMapper;
import com.sg.superperson.dto.Role;
import com.sg.superperson.dto.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDaoDB implements UserDao {
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public User getUserById(int id) {
        try{
            final String SelectUserById = "SELECT * FROM user WHERE id = ?";
            User user = jdbc.queryForObject(SelectUserById, new UserMapper(), id);
            user.setRoles(getRolesForUser(user.getId()));
            return user;
            } catch (DataAccessException ex){
                return null;
            }
    }

    @Override
    public User getUserByUsername(String username) {
        try{
        final String SelectUserByUserName = "SELECT * FROM user WHERE username = ?";
        User user = jdbc.queryForObject(SelectUserByUserName, new UserMapper(), username);   
        user.setRoles(getRolesForUser(user.getId()));
        return user;
        } catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        final String SelectAllUsers = "SELECT * FROM user";
        List<User> users = jdbc.query(SelectAllUsers, new UserMapper());
        for(User user : users){
            user.setRoles(getRolesForUser(user.getId()));
        }
        return users;
    }

    //helper method because user is managing the user-role relationship
    private Set<Role> getRolesForUser(int id) throws DataAccessException{
        final String SelectRolesForUser = "SELECT r.* FROM user_role ur "
                + "JOIN role r ON ur.role_id = r.id "
                + "WHERE ur.user_id = ?";
        Set<Role> roles = new HashSet(jdbc.query(SelectRolesForUser, new RoleMapper(), id));
        
        return roles;
    }
    @Override
    public void updateUser(User user) {
        final String UpdateUser = "UPDATE user SET username = ?, password = ?, enabled = ? WHERE id = ?";
        jdbc.update(UpdateUser,
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.getId());
        
        final String DeleteUserRole = "DELETE FROM user_role WHERE user_id = ?";
        jdbc.update(DeleteUserRole, user.getId());
        for (Role role : user.getRoles()) {
            final String InsertUserRole = "INSERT INTO user_role(user_id, role_id) VALUES (?,?)";
            jdbc.update(InsertUserRole, user.getId(), role.getId());
        }

    }

    @Override
    public void deleteUser(int id) {
        final String DeleteUserRole = "DELETE FROM user_role WHERE user_id = ?";
        final String DeleteUser = "DELETE FROM user WHERE id = ?";
        jdbc.update(DeleteUserRole, id);
        jdbc.update(DeleteUser, id);
    }

    @Override
    @Transactional
    public User createUser(User user) {
        final String InsertUser = "INSERT INTO user(username, password, enabled) VALUES (?,?,?)";
        jdbc.update(InsertUser,
                user.getUsername(),
                user.getPassword(),
                user.isEnabled());
        int newId = jdbc.queryForObject("select LAST_INSERT_ID()", Integer.class);
        user.setId(newId);
        
        for(Role role : user.getRoles()){
            final String InsertUserRole = "INSERT INTO user_role(user_id, role_id) VALUES (?,?)";
            jdbc.update(InsertUserRole, user.getId(), role.getId());
        }
        return user;
                
    }
    
    public static final class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEnabled(rs.getBoolean("enabled"));
            return user;
        }
        
    }
}
