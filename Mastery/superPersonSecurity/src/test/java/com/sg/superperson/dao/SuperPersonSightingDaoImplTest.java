/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superperson.dao;

import com.sg.superperson.dto.Location;
import com.sg.superperson.dto.Org;
import com.sg.superperson.dto.Sighting;
import com.sg.superperson.dto.SuperPerson;
import com.sg.superperson.dto.SuperPower;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author seanking
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SuperPersonSightingDaoImplTest {

    @Autowired
    SuperPowerDao SuperPowerDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    SuperPersonDao superPersonDao;

    @Autowired
    SuperPersonOrganizationDao superOrgDao;

    @Autowired
    SuperPersonSightingDao superSightingDao;

    public SuperPersonSightingDaoImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        
        List<SuperPower> superPowers = SuperPowerDao.getAllSuperPowers();
        for (SuperPower superPower : superPowers) {
            SuperPowerDao.getSuperPower(superPower.getSuperPowerId());
            SuperPowerDao.deleteSuperPower(superPower.getSuperPowerId());
        }
        
         List<SuperPerson> superPersons = superPersonDao.getAllSupers();
        for (SuperPerson superPerson : superPersons) {
            superPersonDao.getSuperPerson(superPerson.getSuperPersonId());
            superPersonDao.deleteSuperPerson(superPerson.getSuperPersonId());
        }

        List<Location> locations = locationDao.getAllLocations();
        for (Location location : locations) {
            locationDao.getLocation(location.getLocationId());
            locationDao.deleteLocation(location.getLocationId());
        }   

        List<Org> organizations = organizationDao.getAllOrganizations();
        for (Org organization : organizations) {
            organizationDao.getOrganization(organization.getOrgId());
            organizationDao.deleteOrganization(organization.getOrgId());
        }
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.getSighting(sighting.getSightingId());
            sightingDao.deleteSighting(sighting.getSightingId());
        }

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createSuperPersonSighting method, of class
     * SuperPersonSightingDaoImpl.
     */
    @Test
    public void testCreateSuperPersonSighting() {
         Location location = new Location();
        location.setName("Dunkin");
        location.setDescription("Test D");
        location.setLongitude(45.0);
        location.setLatitude(40.0);
        location.setStreet("Test street");
        location.setCity("Test city");
        location.setZipcode(11232);
        location.setState("Test State");
        location = locationDao.createLocation(location);
        
        Org organization = new Org();
        organization.setName("Test Name");
        organization.setDescription("Org description");
        organization.setContactInfo("Test info");
        organization.setLocation(location);
        organization = organizationDao.createOrganization(organization);
        
        List<Org> orgs = new ArrayList<>();
        orgs.add(organization);
        
        SuperPower superPower = new SuperPower();
        superPower.setName("Test Power");
        superPower.setDescription("Description");
        superPower = SuperPowerDao.addSuperPower(superPower);
        
        SuperPerson superPerson = new SuperPerson();
        superPerson.setSuperpower(superPower);
        superPerson.setName("Test Super");
        superPerson.setDescription("Super");
        superPerson.setIsVillian(Boolean.getBoolean("Yes"));
        superPerson.setOrganizations(orgs);
        superPerson = superPersonDao.addSuperPerson(superPerson);
        List<SuperPerson> supers = new ArrayList<>();
        supers.add(superPerson);
        
        Location location2 = new Location();
        location2.setName("Test");
        location2.setDescription("Test D");
        location2.setLongitude(45.0);
        location2.setLatitude(40.0);
        location2.setStreet("Test street");
        location2.setCity("Test city");
        location2.setZipcode(11232);
        location2.setState("Test State");
        location2 = locationDao.createLocation(location2);
        
        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setTime(LocalDateTime.now());
        sighting.setLocation(location2);
        sighting.setSuperPersons(supers);
        sighting = sightingDao.addSighting(sighting);
     
        
        
    }

    /**
     * Test of deleteSuperPersonSighting method, of class
     * SuperPersonSightingDaoImpl.
     */
    @Test
    public void testDeleteSuperPersonSighting() {
         Location location = new Location();
        location.setName("Dunkin");
        location.setDescription("Test D");
        location.setLongitude(45.0);
        location.setLatitude(40.0);
        location.setStreet("Test street");
        location.setCity("Test city");
        location.setZipcode(11232);
        location.setState("Test State");
        location = locationDao.createLocation(location);
        
        Org organization = new Org();
        organization.setName("Test Name");
        organization.setDescription("Org description");
        organization.setContactInfo("Test info");
        organization.setLocation(location);
        organization = organizationDao.createOrganization(organization);
        
        List<Org> orgs = new ArrayList<>();
        orgs.add(organization);
        
        SuperPower superPower = new SuperPower();
        superPower.setName("Test Power");
        superPower.setDescription("Description");
        superPower = SuperPowerDao.addSuperPower(superPower);
        
        SuperPerson superPerson = new SuperPerson();
        superPerson.setSuperpower(superPower);
        superPerson.setName("Test Super");
        superPerson.setDescription("Super");
        superPerson.setIsVillian(Boolean.getBoolean("Yes"));
        superPerson.setOrganizations(orgs);
        superPerson = superPersonDao.addSuperPerson(superPerson);
        List<SuperPerson> supers = new ArrayList<>();
        supers.add(superPerson);
        
        Location location2 = new Location();
        location2.setName("Test");
        location2.setDescription("Test D");
        location2.setLongitude(45.0);
        location2.setLatitude(40.0);
        location2.setStreet("Test street");
        location2.setCity("Test city");
        location2.setZipcode(11232);
        location2.setState("Test State");
        location2 = locationDao.createLocation(location2);
        
        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setTime(LocalDateTime.now());
        sighting.setLocation(location2);
        sighting.setSuperPersons(supers);
        sighting = sightingDao.addSighting(sighting);
     
        
        superSightingDao.deleteSuperPersonSighting(sighting.getSightingId(), superPerson.getSuperPersonId());
        
    }

}
