/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.service;

import com.sg.superperson.dao.OrganizationDao;
import com.sg.superperson.dto.Org;
import com.sg.superperson.dto.SuperPerson;

import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author seanking
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {
    
    private OrganizationDao o;
    
    public OrganizationServiceImpl(OrganizationDao o){
            this.o = o;
        
    }

    @Override
    public Org createOrganization(Org organization) {
        return o.createOrganization(organization);
    }

    @Override
    public Org getOrganization(int organizationId) {
        return o.getOrganization(organizationId);
    }

    @Override
    public List<Org> getAllOrganizations() {
        return o.getAllOrganizations();
    }

    @Override
    public List<Org> getAllOrganizationsOfASuper(SuperPerson superperson) {
        return o.getAllOrganizationsOfASuper(superperson);
    }
    
    @Override
    public void editOrganization(Org organization) {
      o.editOrganization(organization);

        
    }
    

    @Override
    public void deleteOrganization(int organizationId) {
        o.deleteOrganization(organizationId);

    }

    

    

}
