/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.service;

import com.sg.superperson.dao.OrganizationDao;
import com.sg.superperson.dao.SuperPersonDao;
import com.sg.superperson.dao.SuperPowerDao;
import com.sg.superperson.dto.Org;
import com.sg.superperson.dto.SuperPerson;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SuperPersonServiceImpl implements SuperPersonService {
    private SuperPersonDao superperson;
    private SuperPowerDao power;
    private OrganizationDao organization;
    
    public SuperPersonServiceImpl(SuperPersonDao superperson,SuperPowerDao power, 
        OrganizationDao organization){
        this.superperson = superperson;
        this.power = power;
        this.organization = organization;
    }
    
    

    @Override
    public SuperPerson addSuperPerson(SuperPerson superPerson) {
        superperson.addSuperPerson(superPerson);
       return superPerson;
                
       
    }

    @Override
    public SuperPerson getSuperPerson(int superPersonId) {
      return  superperson.getSuperPerson(superPersonId);
        
    }

    @Override
    public List<SuperPerson> getAllSupers() {
       return superperson.getAllSupers();
    }

    @Override
    public List<SuperPerson> getAllMembers(Org organization) {
        return superperson.getAllMembers(organization);
    }


    @Override
    public void updateSuperPerson(SuperPerson superPerson) {
        superperson.updateSuperPerson(superPerson);
    }

    @Override
    public void deleteSuperPerson(int superPersonId) {
        superperson.deleteSuperPerson(superPersonId);
    }
    
}
