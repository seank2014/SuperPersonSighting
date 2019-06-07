/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.dao;
import com.sg.superperson.dto.SuperPerson;
import com.sg.superperson.dto.Org;


/**
 *
 * @author seanking
 */
public interface SuperPersonOrganizationDao {

    //create
    public void createSuperPersonOrganization(Org organization, SuperPerson superPerson);
    //delete 
    public void deleteSuperPersonOrganization(int superPersonId, int organizationId);

}
