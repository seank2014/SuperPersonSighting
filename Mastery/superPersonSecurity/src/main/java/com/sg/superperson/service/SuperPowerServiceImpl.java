/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.service;

import com.sg.superperson.dao.SuperPowerDao;
import com.sg.superperson.dto.SuperPower;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author seanking
 */
@Service
public class SuperPowerServiceImpl implements SuperPowerService {
    
    private SuperPowerDao powers;
    
    public SuperPowerServiceImpl (SuperPowerDao powers){
        this.powers = powers;
    
}

    @Override
    public SuperPower addSuperPower(SuperPower superPower) {
        return powers.addSuperPower(superPower);
    }

    @Override
    public SuperPower getSuperPower(int superPowerId) {
        return powers.getSuperPower(superPowerId);
    }

    @Override
    public List<SuperPower> getAllSuperPowers() {
        return powers.getAllSuperPowers();
    }
    
    
    @Override
    public void editPowers(SuperPower superPower) {
        powers.editPowers(superPower);
    }

    @Override
    public void deleteSuperPower(int superPowerId) {
        powers.deleteSuperPower(superPowerId);
    }

    
}
