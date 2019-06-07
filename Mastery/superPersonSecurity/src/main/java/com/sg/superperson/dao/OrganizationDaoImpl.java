/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.dao;

import com.sg.superperson.dao.LocationDaoImpl.LocationMapper;
import com.sg.superperson.dto.Location;
import com.sg.superperson.dto.Org;
import com.sg.superperson.dto.SuperPerson;
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
public class OrganizationDaoImpl implements OrganizationDao {

    private final String createOrganization = "INSERT INTO Org (Name, Description, ContactInfo, LocationId) VALUES (?,?,?,?)";
    private final String getOrganization = "SELECT * FROM Org WHERE OrgId = ? ";
    private final String getAllOrganizations = "SELECT * FROM Org";
    private final String getAllOrganizationsOfASuper = "SELECT * FROM Org INNER JOIN SuperPersonOrg ON"
            + " SuperPersonOrg.OrgId = Org.OrgId WHERE SuperPersonOrg.SuperPersonId = ?";
    private final String editOrganization = "UPDATE Org SET Name = ?, Description = ?, ContactInfo = ?, "
            + "LocationId = ? WHERE OrgId = ?";
    private final String deleteOrganization = "DELETE FROM Org WHERE OrgId = ? ";
    

    @Autowired
    JdbcTemplate jdbc;
   
    @Override
    @Transactional
    public Org createOrganization(Org org) {
        jdbc.update(createOrganization,
                org.getName(),
                org.getDescription(),
                org.getContactInfo(),
                org.getLocation().getLocationId());
        int newId = jdbc.queryForObject("Select LAST_INSERT_ID()", Integer.class);
        org.setOrgId(newId);
        return org;
    }

    @Override
    public Org getOrganization(int organizationId) {
        try {
            Org org = jdbc.queryForObject(getOrganization, new OrganizationMapper(), organizationId);
            org.setLocation(getLocationForOrg(organizationId));
            return org;
        } catch (DataAccessException ex) {
            return null;
        }

    }

    private Location getLocationForOrg(int organizationId) {
        final String getLocationForOrg = "SELECT l.* FROM Location l "
                + "JOIN Org o ON o.LocationId = l.LocationId WHERE o.OrgId = ? ";
        return jdbc.queryForObject(getLocationForOrg, new LocationMapper(), organizationId);
    }

    private void associateLocationAndOrg(List<Org> orgs) {
        for (Org org : orgs) {
            org.setLocation(getLocationForOrg(org.getOrgId()));
        }
    }

    @Override
    public List<Org> getAllOrganizations() {
        List<Org> orgs = jdbc.query(getAllOrganizations, new OrganizationMapper());
        associateLocationAndOrg(orgs);
        return orgs;
    }

    @Override
    public List<Org> getAllOrganizationsOfASuper(SuperPerson superperson) {
        List<Org> org = jdbc.query(getAllOrganizationsOfASuper, new OrganizationMapper(), superperson.getSuperPersonId());
            associateLocationAndOrg(org);

    return org ;
    }
    
    @Override
    public void editOrganization(Org org) {
        jdbc.update(editOrganization,
                org.getName(),
                org.getDescription(),
                org.getContactInfo(),
                org.getLocation().getLocationId(),
                org.getOrgId());
    }

    @Override
    @Transactional
    public void deleteOrganization(int organizationId) {
        final String deleteSuperOrg = "DELETE FROM SuperPersonOrg WHERE orgId = ?";
        jdbc.update(deleteSuperOrg, organizationId);

        jdbc.update(deleteOrganization, organizationId);

    }

    public static final class OrganizationMapper implements RowMapper<Org> {

        @Override
        public Org mapRow(ResultSet rs, int index) throws SQLException {
            Org org = new Org();
            org.setOrgId(rs.getInt("OrgId"));
            org.setName(rs.getString("Name"));
            org.setDescription(rs.getString("Description"));
            org.setContactInfo(rs.getString("ContactInfo"));
            return org;

        }

    }

}
