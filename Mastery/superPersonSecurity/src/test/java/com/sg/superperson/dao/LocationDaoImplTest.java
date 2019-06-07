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
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author seanking
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationDaoImplTest {

    @Autowired
    SuperPowerDao superPowerDao;

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

    public LocationDaoImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        
     
     List <SuperPower> superPowers = superPowerDao.getAllSuperPowers();
     for(SuperPower superPower : superPowers){
         superPowerDao.getSuperPower(superPower.getSuperPowerId());
         superPowerDao.deleteSuperPower(superPower.getSuperPowerId());
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
     * Test of getLocation method, of class LocationDaoImpl.
     */
    @Test
    @Transactional
    public void testCreateAndGetLocation() {
        Location location = new Location();
        location.setName("Test");
        location.setDescription("Test D");
        location.setLongitude(45.0);
        location.setLatitude(40.0);
        location.setStreet("Test street");
        location.setCity("Test city");
        location.setZipcode(11232);
        location.setState("Test State");
        location = locationDao.createLocation(location);
        
        Location fromDao = locationDao.getLocation(location.getLocationId());
        assertEquals(location, fromDao);     
    }

    /**
     * Test of getAllLocations method, of class LocationDaoImpl.
     */
    @Test
     @Transactional
    public void testGetAllLocations() {
         Location location = new Location();
         location.setName("Test");
        location.setDescription("Test D");
        location.setLongitude(45.0);
        location.setLatitude(40.0);
        location.setStreet("Test street");
        location.setCity("Test city");
        location.setZipcode(11232);
        location.setState("Test State");
        location = locationDao.createLocation(location);
        
        Location location2 = new Location();
        location2.setName("Another test");
        location2.setDescription("Another est D");
        location2.setLongitude(42.0);
        location2.setLatitude(50.0);
        location2.setStreet("Another test street");
        location2.setCity("Another test city");
        location2.setZipcode(11232);
        location2.setState("Another test State");
        location2 = locationDao.createLocation(location2);
        
        List<Location> locations = locationDao.getAllLocations();
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
    }
    
    

    /**
     * Test of getAllLocationsByHero method, of class LocationDaoImpl.
     */
    @Test
     @Transactional
    public void testGetAllLocationsByHero() {
//        Location location = new Location();
//        location.setName("Dunkin");
//        location.setDescription("Test D");
//        location.setLongitude(45.0);
//        location.setLatitude(40.0);
//        location.setStreet("Test street");
//        location.setCity("Test city");
//        location.setZipcode(11232);
//        location.setState("Test State");
//        location = locationDao.createLocation(location);
//        
//        Org organization = new Org();
//        organization.setName("Test Name");
//        organization.setDescription("Org description");
//        organization.setContactInfo("Test info");
//        organization.setLocation(location);
//        organization = organizationDao.createOrganization(organization);
//        
//        List<Org> orgs = new ArrayList<>();
//        orgs.add(organization);
//        
//        Location location2 = new Location();
//        location2.setName("Test");
//        location2.setDescription("Test D");
//        location2.setLongitude(45.0);
//        location2.setLatitude(40.0);
//        location2.setStreet("Test street");
//        location2.setCity("Test city");
//        location2.setZipcode(11232);
//        location2.setState("Test State");
//        location2 = locationDao.createLocation(location2);
//        
//        Location location3 = new Location();
//        location3.setName("Another Test");
//        location3.setDescription("Test D");
//        location3.setLongitude(45.0);
//        location3.setLatitude(40.0);
//        location3.setStreet("Test street");
//        location3.setCity("Test city");
//        location3.setZipcode(11232);
//        location3.setState("Test State");
//        location3 = locationDao.createLocation(location3);
//        
//        Sighting sighting = new Sighting();
//        sighting.setDate(LocalDate.now());
//        sighting.setTime(LocalDateTime.now());
//        sighting.setLocation(location2);
//        sighting = sightingDao.addSighting(sighting);
//        
//        Sighting sighting2 = new Sighting();
//        sighting2.setDate(LocalDate.now());
//        sighting2.setTime(LocalDateTime.now());
//        sighting2.setLocation(location3);
//        sighting2 = sightingDao.addSighting(sighting2);
//        
//        List<Sighting> sight = new ArrayList<>();
//        sight.add(sighting);
//        sight.add(sighting2);
//        
//        SuperPower superPower = new SuperPower();
//        superPower.setName("Test Power");
//        superPower.setDescription("Description");
//        superPower = superPowerDao.addSuperPower(superPower);
//        
//        SuperPerson superPerson = new SuperPerson();
//        superPerson.setSuperpower(superPower);
//        superPerson.setName("Test Super");
//        superPerson.setDescription("Super");
//        superPerson.setIsVillian(Boolean.getBoolean("Yes"));
//        superPerson.setOrganizations(orgs);
//        superPerson.setSightings(sight);
//        superPerson = superPersonDao.addSuperPerson(superPerson);
//
//       
//        List <Location> heroLocations = locationDao.getAllLocationsByHero(superPerson);
//        
//        assertEquals(2, heroLocations.size());
//        assertTrue(heroLocations.contains(location2));
//        assertTrue(heroLocations.contains(location3));   
//        assertFalse(heroLocations.contains(location));
    }
    
    /**
     * Test of changeLocation method, of class LocationDaoImpl.
     */
    @Test
     @Transactional
    public void testChangeLocation() {
        Location location = new Location();
        location.setName("Test");
        location.setDescription("Test D");
        location.setLongitude(45.0);
        location.setLatitude(40.0);
        location.setStreet("Test street");
        location.setCity("Test city");
        location.setZipcode(11232);
        location.setState("Test State");
        location = locationDao.createLocation(location);
        
        Location fromDao = locationDao.getLocation(location.getLocationId());
        assertEquals(location, fromDao);
        
        location.setDescription("Another description");
        locationDao.changeLocation(location);
        
        assertNotEquals(location, fromDao);
        
        fromDao = locationDao.getLocation(location.getLocationId());
        
        assertEquals(location, fromDao);
    }

    /**
     * Test of deleteLocation method, of class LocationDaoImpl.
     */
    @Test
     @Transactional
    public void testDeleteLocation() {
 
        Location location = new Location();
        location.setName("Test");
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
        superPower = superPowerDao.addSuperPower(superPower);
        
        SuperPerson superPerson = new SuperPerson();
        superPerson.setSuperpower(superPower);
        superPerson.setName("Test Super");
        superPerson.setDescription("Super");
        superPerson.setIsVillian(Boolean.getBoolean("Yes"));
        superPerson.setOrganizations(orgs);
        superPerson = superPersonDao.addSuperPerson(superPerson);
        
        List<SuperPerson> superPersons = new ArrayList<>();
        superPersons.add(superPerson);
        
        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setTime(LocalDateTime.now());
        sighting.setLocation(location);
        sighting.setSuperPersons(superPersons);
        sighting = sightingDao.addSighting(sighting); 
        
        Location fromDao = locationDao.getLocation(location.getLocationId());
        
        organizationDao.deleteOrganization(organization.getOrgId());
        sightingDao.deleteSighting(sighting.getSightingId());
        locationDao.deleteLocation(location.getLocationId());
        
        fromDao = locationDao.getLocation(location.getLocationId());
        assertNull(fromDao);
    }

}
