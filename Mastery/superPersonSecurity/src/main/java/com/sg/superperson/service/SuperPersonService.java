/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.service;

import com.sg.superperson.dto.Org;
import com.sg.superperson.dto.SuperPerson;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author seanking
 */
public interface SuperPersonService {
    
    public SuperPerson addSuperPerson(SuperPerson superPerson);

    public SuperPerson getSuperPerson(int superPersonId);

    public List<SuperPerson> getAllSupers();

    public List<SuperPerson> getAllMembers(Org organization);

    public void updateSuperPerson(SuperPerson superPerson);

    public void deleteSuperPerson(int superPersonId);

    
}
