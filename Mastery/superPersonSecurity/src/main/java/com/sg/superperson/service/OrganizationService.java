/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.service;

import com.sg.superperson.dto.Org;
import com.sg.superperson.dto.SuperPerson;
import java.util.List;

/**
 *
 * @author seanking
 */
public interface OrganizationService {

    public Org createOrganization(Org organization);

    public Org getOrganization(int organizationId);

    public List<Org> getAllOrganizations();

    public List<Org> getAllOrganizationsOfASuper(SuperPerson superperson);

    public void editOrganization(Org org);

    public void deleteOrganization(int organizationId);

}
