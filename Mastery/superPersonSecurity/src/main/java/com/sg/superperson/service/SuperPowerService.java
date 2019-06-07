/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.service;

import com.sg.superperson.dto.SuperPower;
import java.util.List;

/**
 *
 * @author seanking
 */
public interface SuperPowerService {
        
public SuperPower addSuperPower(SuperPower superPower);
    

public SuperPower getSuperPower(int superPowerId);
    
 
public List<SuperPower> getAllSuperPowers();

public void editPowers (SuperPower superPower);

public void deleteSuperPower(int superPowerId);
    
}
