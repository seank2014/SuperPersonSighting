/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.dto;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author seanking
 */
public class SuperPerson {
    
    private int superPersonId;
    
    @NotBlank(message= "Name must not be empty")
    @Size(max = 30, message = "Name must be less than 30 characters")
    private String name;
    
    @NotBlank(message = "Description must not be empty")
    @Size(max = 50, message = "Description must be less than 50 chracters")
    private String description;
    
    
    private SuperPower superpower;
    
    private List <Org> organizations;
    
    private Boolean isVillian;

    public int getSuperPersonId() {
        return superPersonId;
    }

    public void setSuperPersonId(int superPersonId) {
        this.superPersonId = superPersonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SuperPower getSuperpower() {
        return superpower;
    }

    public void setSuperpower(SuperPower superpower) {
        this.superpower = superpower;
    }

    public List<Org> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Org> organizations) {
        this.organizations = organizations;
    }

    public Boolean getIsVillian() {
        return isVillian;
    }

    public void setIsVillian(Boolean isVillian) {
        this.isVillian = isVillian;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.superPersonId;
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.description);
        hash = 53 * hash + Objects.hashCode(this.superpower);
        hash = 53 * hash + Objects.hashCode(this.organizations);
        hash = 53 * hash + Objects.hashCode(this.isVillian);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SuperPerson other = (SuperPerson) obj;
        if (this.superPersonId != other.superPersonId) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.superpower, other.superpower)) {
            return false;
        }
        if (!Objects.equals(this.organizations, other.organizations)) {
            return false;
        }
        if (!Objects.equals(this.isVillian, other.isVillian)) {
            return false;
        }
        return true;
    }  
}
