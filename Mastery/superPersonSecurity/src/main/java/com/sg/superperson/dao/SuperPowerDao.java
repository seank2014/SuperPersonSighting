/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.dao;

import com.sg.superperson.dto.SuperPower;
import java.util.List;

/**
 *
 * @author seanking
 */
public interface SuperPowerDao {

//Create
    public SuperPower addSuperPower(SuperPower superPower);

// Read
    public SuperPower getSuperPower(int superPowerId);

    public List<SuperPower> getAllSuperPowers();

//Update
    public void editPowers(SuperPower superpower);

//delete
    public void deleteSuperPower(int superPowerId);

}
