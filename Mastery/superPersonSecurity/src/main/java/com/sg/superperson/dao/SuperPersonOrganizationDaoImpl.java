/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.dao;

import com.sg.superperson.dto.Org;
import com.sg.superperson.dto.SuperPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SuperPersonOrganizationDaoImpl implements SuperPersonOrganizationDao {

    @Autowired
    JdbcTemplate jdbc;
    
    final String createSuperPersonOrganization = "INSERT INTO SuperPersonOrg(OrgId, SuperPersonId) VALUES (?,?)";
    final String deleteSuperPersonOrganization = "DELETE so.* FROM SuperPersonOrg so WHERE so.OrgId = ? AND so.SuperPersonId = ?";
  

        @Override
        @Transactional
        public void createSuperPersonOrganization(Org organization, SuperPerson superPerson) {
        jdbc.update(createSuperPersonOrganization,
                organization.getOrgId(),
                superPerson.getSuperPersonId());
    }
        
       

    @Override
    public void deleteSuperPersonOrganization(int organizationId, int superPersonId) {
        jdbc.update(deleteSuperPersonOrganization, organizationId, superPersonId);
        
    }


}
