/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.dao;

import com.sg.superperson.dto.Role;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RoleDaoDB implements RoleDao {
    @Autowired
    JdbcTemplate jdbc;
            

    @Override
    public Role getRoleById(int id) {
        try{
            final String SelectRoleById = "SELECT * FROM role WHERE id = ?";
            return jdbc.queryForObject(SelectRoleById, new RoleMapper(), id);
        } catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public Role getRoleByRole(String role) {
        try{
            final String SelectRoleByRole = "SELECT * FROM role WHERE role = ?";
            return jdbc.queryForObject(SelectRoleByRole, new RoleMapper(), role);
        } catch (DataAccessException ex){
            return null;
        }

    }

    @Override
    public List<Role> getAllRoles() {
        final String SelectAllRoles = "SELECT * FROM role";
        return jdbc.query(SelectAllRoles, new RoleMapper());
    }

    
    @Override
    public void deleteRole(int id) {
        final String DeleteUserRole = "DELETE FROM user_role WHERE role_id = ?";
        final String DeleteRole = "DELETE FROM role WHERE id = ?";
        jdbc.update(DeleteUserRole, id);
        jdbc.update(DeleteRole, id);
    }

    @Override
    public void updateRole(Role role) {
        final String UpdateRole = "UPDATE role SET role = ? WHERE id = ?";
        jdbc.update(UpdateRole, 
                role.getRole(),
                role.getId());

    }

    @Override
    @Transactional
    public Role createRole(Role role) {
        final String InsertRole = "INSERT INTO role(role) VALUES(?)";
        jdbc.update(InsertRole, role.getRole());
        int newId = jdbc.queryForObject("select LAST_INSERT_ID()", Integer.class);
        role.setId(newId);
        return role;
    }
    
    public static final class RoleMapper implements RowMapper<Role>{

        @Override
        public Role mapRow(ResultSet rs, int i) throws SQLException {
            Role role = new Role();
            role.setId(rs.getInt("id"));
            role.setRole(rs.getString("role"));
            return role;
        }
}
}
