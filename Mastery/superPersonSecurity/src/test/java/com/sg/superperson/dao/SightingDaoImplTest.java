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
import java.time.format.DateTimeFormatter;
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
public class SightingDaoImplTest {

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

    public SightingDaoImplTest() {
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

        List<SuperPerson> superPersons = superPersonDao.getAllSupers();
        for (SuperPerson superPerson : superPersons) {
            superPersonDao.getSuperPerson(superPerson.getSuperPersonId());
            superPersonDao.deleteSuperPerson(superPerson.getSuperPersonId());
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getSighting method, of class SightingDaoImpl.
     */
    @Test
    public void testAddandGetSighting() {
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
        List<SuperPerson> superPersons = new ArrayList<>();
        superPersons.add(superPerson);
        
        Location location2 = new Location();
        location2.setName("Dunkin");
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
        sighting.setTime(LocalDateTime.now().withNano(0));
        sighting.setLocation(location2);
        sighting.setSuperPersons(superPersons);
        sighting = sightingDao.addSighting(sighting); 

        Sighting fromDao = sightingDao.getSighting(sighting.getSightingId());
        assertEquals(sighting, fromDao);
    }

    /**
     * Test of getAllSightings method, of class SightingDaoImpl.
     */
    @Test
    public void testGetAllSightings() {
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
        
        Location location2 = new Location();
        location2.setName("Dunkin");
        location2.setName("Test");
        location2.setDescription("Test D");
        location2.setLongitude(45.0);
        location2.setLatitude(40.0);
        location2.setStreet("Test street");
        location2.setCity("Test city");
        location2.setZipcode(11232);
        location2.setState("Test State");
        location2 = locationDao.createLocation(location2);
        
        Location location3 = new Location();
        location3.setName("Other test");
        location3.setDescription("Another est D");
        location3.setLongitude(42.0);
        location3.setLatitude(50.0);
        location3.setStreet("Another test street");
        location3.setCity("Another test city");
        location3.setZipcode(11232);
        location3.setState("Another test State");
        location3 = locationDao.createLocation(location3);
        
        
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
      
        SuperPower superPower2 = new SuperPower();
        superPower2.setName("Other Power");
        superPower2.setDescription("Another description");
        superPower2 = SuperPowerDao.addSuperPower(superPower2);
        
        SuperPerson superPerson2 = new SuperPerson();
        superPerson2.setSuperpower(superPower2);
        superPerson2.setName("Another Super");
        superPerson2.setDescription("SuperDuper");
        superPerson2.setIsVillian(Boolean.getBoolean("No"));
        superPerson2.setOrganizations(orgs);
        superPerson2 = superPersonDao.addSuperPerson(superPerson2);
        
        List<SuperPerson> supers = new ArrayList<>();
        supers.add(superPerson);
        supers.add(superPerson2);
        
        
        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setTime(LocalDateTime.now().withNano(0));
        sighting.setLocation(location2);
        sighting.setSuperPersons(supers);
        sighting = sightingDao.addSighting(sighting); 

        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDate.now());
        sighting2.setTime(LocalDateTime.now().withNano(0));
        sighting2.setLocation(location3);
        sighting2.setSuperPersons(supers);
        sighting2 = sightingDao.addSighting(sighting2);

        List<Sighting> sightings = sightingDao.getAllSightings();

        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));

    }

    @Test
    public void getAllSightingByDate() {
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
        
        Location location2 = new Location();
        location2.setName("Dunkin");
        location2.setName("Test");
        location2.setDescription("Test D");
        location2.setLongitude(45.0);
        location2.setLatitude(40.0);
        location2.setStreet("Test street");
        location2.setCity("Test city");
        location2.setZipcode(11232);
        location2.setState("Test State");
        location2 = locationDao.createLocation(location2);
        
         Location location3 = new Location();
        location3.setName("Other test");
        location3.setDescription("Another est D");
        location3.setLongitude(42.0);
        location3.setLatitude(50.0);
        location3.setStreet("Another test street");
        location3.setCity("Another test city");
        location3.setZipcode(11232);
        location3.setState("Another test State");
        location3 = locationDao.createLocation(location3);

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
      
        SuperPower superPower2 = new SuperPower();
        superPower2.setName("Other Power");
        superPower2.setDescription("Another description");
        superPower2 = SuperPowerDao.addSuperPower(superPower2);
        
        SuperPerson superPerson2 = new SuperPerson();
        superPerson2.setSuperpower(superPower2);
        superPerson2.setName("Another Super");
        superPerson2.setDescription("SuperDuper");
        superPerson2.setIsVillian(Boolean.getBoolean("No"));
        superPerson2.setOrganizations(orgs);
        superPerson2 = superPersonDao.addSuperPerson(superPerson2);
        
        List<SuperPerson> supers = new ArrayList<>();
        supers.add(superPerson);
        supers.add(superPerson2);
        
        
        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setTime(LocalDateTime.now().withNano(0));
        sighting.setLocation(location2);
        sighting.setSuperPersons(supers);
        sighting = sightingDao.addSighting(sighting); 

        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDate.now());
        sighting2.setTime(LocalDateTime.now().withNano(0));
        sighting2.setLocation(location3);
        sighting2.setSuperPersons(supers);
        sighting2 = sightingDao.addSighting(sighting2);

        List<Sighting> sightings = sightingDao.getAllSightingByDate(LocalDate.now());
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));

    }

   

    /**
     * Test of changeSighting method, of class SightingDaoImpl.
     */
    @Test
    public void testChangeSighting() {
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
        List<SuperPerson> superPersons = new ArrayList<>();
        superPersons.add(superPerson);
        
        Location location2 = new Location();
        location2.setName("Dunkin");
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
        sighting.setTime(LocalDateTime.now().withNano(0));
        sighting.setLocation(location2);
        sighting.setSuperPersons(superPersons);
        sighting = sightingDao.addSighting(sighting); 

        Sighting fromDao = sightingDao.getSighting(sighting.getSightingId());
        assertEquals(sighting, fromDao);
        
        sighting.setDate(LocalDate.parse("2019-05-09"));
        sightingDao.changeSighting(sighting);

        assertNotEquals(sighting, fromDao);

        fromDao = sightingDao.getSighting(sighting.getSightingId());
        assertEquals(sighting, fromDao);
    }

    /**
     * Test of deleteSighting method, of class SightingDaoImpl.
     */
    @Test
    public void testDeleteSighting() {
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
        List<SuperPerson> superPersons = new ArrayList<>();
        superPersons.add(superPerson);
        
        Location location2 = new Location();
        location2.setName("Dunkin");
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
        sighting.setTime(LocalDateTime.now().withNano(0));
        sighting.setLocation(location2);
        sighting.setSuperPersons(superPersons);
        sighting = sightingDao.addSighting(sighting); 

        Sighting fromDao = sightingDao.getSighting(sighting.getSightingId());
        
        superSightingDao.deleteSuperPersonSighting(sighting.getSightingId(), superPerson.getSuperPersonId());
        sightingDao.deleteSighting(sighting.getSightingId());

        fromDao = sightingDao.getSighting(sighting.getSightingId());
        assertNull(fromDao);
    }

     @Test
    public void getAllSuperPersonByDate() {
    }
}
