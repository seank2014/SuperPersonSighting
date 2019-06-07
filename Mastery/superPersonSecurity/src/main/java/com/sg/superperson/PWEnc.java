/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author seanking
 */
public class PWEnc {
    
    /*"PWEnc" is short for password encoder
    this class is a small program we are running to update passwords in the database to be 
    encoded by our password encoder*/
    
    public static void main(String[] args){
        String clearTxtPw = "password";
        //BCrpyt
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPw = encoder.encode(clearTxtPw);
        System.out.println(hashedPw);
    }
    
}
