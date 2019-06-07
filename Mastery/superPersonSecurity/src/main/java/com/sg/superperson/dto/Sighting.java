/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 *
 * @author seanking
 */
public class Sighting {

    private int sightingId;
    
    private Location location;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime time;
    
    private List<SuperPerson> superPersons;

    public int getSightingId() {
        return sightingId;
    }

    public void setSightingId(int sightingId) {
        this.sightingId = sightingId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public List<SuperPerson> getSuperPersons() {
        return superPersons;
    }

    public void setSuperPersons(List<SuperPerson> superPersons) {
        this.superPersons = superPersons;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.sightingId;
        hash = 71 * hash + Objects.hashCode(this.location);
        hash = 71 * hash + Objects.hashCode(this.date);
        hash = 71 * hash + Objects.hashCode(this.time);
        hash = 71 * hash + Objects.hashCode(this.superPersons);
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
        final Sighting other = (Sighting) obj;
        if (this.sightingId != other.sightingId) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.time, other.time)) {
            return false;
        }
        if (!Objects.equals(this.superPersons, other.superPersons)) {
            return false;
        }
        return true;
    }

    
}
