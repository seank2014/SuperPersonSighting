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
public class SuperPowerDaoImplTest {

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

    public SuperPowerDaoImplTest() {
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
     for(SuperPower superPower : superPowers){
         SuperPowerDao.getSuperPower(superPower.getSuperPowerId());
         SuperPowerDao.deleteSuperPower(superPower.getSuperPowerId());
     }
     
     List <Location> locations = locationDao.getAllLocations();
     for(Location location: locations){
         locationDao.getLocation(location.getLocationId());
         locationDao.deleteLocation(location.getLocationId());
     }
     
     List<Org> organizations = organizationDao.getAllOrganizations();
     for(Org organization: organizations){
         organizationDao.getOrganization(organization.getOrgId());
         organizationDao.deleteOrganization(organization.getOrgId());
     }
     
     List <Sighting> sightings = sightingDao.getAllSightings();
     for(Sighting sighting: sightings){
         sightingDao.getSighting(sighting.getSightingId());
         sightingDao.deleteSighting(sighting.getSightingId());
     }
     
     List<SuperPerson> superPersons = superPersonDao.getAllSupers();
     for(SuperPerson superPerson: superPersons){
         superPersonDao.getSuperPerson(superPerson.getSuperPersonId());
         superPersonDao.deleteSuperPerson(superPerson.getSuperPersonId());
     }     
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getSuperPower method, of class SuperPowerDaoImpl.
     */
    @Test
    public void testAddGetSuperPower() {
        SuperPower superPower = new SuperPower();
        superPower.setName("Test power");
        superPower.setDescription("Powerful");
        superPower = SuperPowerDao.addSuperPower(superPower);
        
        SuperPower fromDao =  SuperPowerDao.getSuperPower(superPower.getSuperPowerId());
        
        assertEquals(superPower, fromDao);
        
        /*
        Step1 : create new object and add its detals
        Step2 : assign new object to add funtion
        step3 : add fromDao assign to dao to get object
        assert fromDao and new superpower object were equal
        
        */

       
    }

    /**
     * Test of getAllSuperPowers method, of class SuperPowerDaoImpl.
     */
    @Test
    public void testGetAllSuperPowers() {
        SuperPower superPower = new SuperPower();
        superPower.setName("Test Power");
        superPower.setDescription("Powerful");
        superPower = SuperPowerDao.addSuperPower(superPower);
        
        SuperPower superPower2 = new SuperPower();
        superPower2.setName("Another Power");
        superPower2.setDescription("Powerful");

        superPower2 = SuperPowerDao.addSuperPower(superPower2);
        
        List<SuperPower> superPowers = SuperPowerDao.getAllSuperPowers();
        assertEquals(2, superPowers.size());
        assertTrue(superPowers.contains(superPower));
        assertTrue(superPowers.contains(superPower2));

    }

    /**
     * Test of deleteSuperPower method, of class SuperPowerDaoImpl.
     */
    @Test
    public void testDeleteSuperPower() {
        SuperPower superPower = new SuperPower();
        superPower.setName("Test Power");
        superPower.setDescription("Powerful");
        superPower = SuperPowerDao.addSuperPower(superPower);
        
        Location location = new Location();
        location.setName("Starbucks");
        location.setDescription("Test D");
        location.setLongitude(45.0);
        location.setLatitude(40.0);
        location.setStreet("Test street");
        location.setCity("Test city");
        location.setZipcode(11232);
        location.setState("Test State");
        location = locationDao.createLocation(location);
      
        Location location2 = new Location();
        location2.setName("Dunkin");
        location2.setDescription("Another est D");
        location2.setLongitude(42.0);
        location2.setLatitude(50.0);
        location2.setStreet("Another test street");
        location2.setCity("Another test city");
        location2.setZipcode(11232);
        location2.setState("Another test State");
        location2 = locationDao.createLocation(location2);


        Org organization = new Org();
        organization.setName("Test org");
        organization.setDescription("Description");
        organization.setContactInfo("contact info");
        organization.setLocation(location);
        organization = organizationDao.createOrganization(organization);
        List<Org> orgs = new ArrayList<>();
        orgs.add(organization);
        
        SuperPerson superPerson = new SuperPerson();
        superPerson.setSuperpower(superPower);
        superPerson.setName("Test Super");
        superPerson.setDescription("Super");
        superPerson.setIsVillian(Boolean.getBoolean("Yes"));
        superPerson.setOrganizations(orgs);
        superPerson = superPersonDao.addSuperPerson(superPerson);
        
        List<SuperPerson> superpersons = new ArrayList<>();
        superpersons.add(superPerson);
        
        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setTime(LocalDateTime.now());
        sighting.setLocation(location2);
        sighting.setSuperPersons(superpersons);
        sighting = sightingDao.addSighting(sighting); 
    
        SuperPower fromDao = SuperPowerDao.getSuperPower(superPower.getSuperPowerId());
        assertEquals(superPower, fromDao);
        
       SuperPowerDao.deleteSuperPower(superPower.getSuperPowerId());

        fromDao = SuperPowerDao.getSuperPower(superPower.getSuperPowerId());
        assertNull(fromDao);
    }

}
