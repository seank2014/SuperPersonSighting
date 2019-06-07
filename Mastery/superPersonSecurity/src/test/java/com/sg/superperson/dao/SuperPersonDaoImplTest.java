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
public class SuperPersonDaoImplTest {
    
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
    
    public SuperPersonDaoImplTest() {
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
     * Test of getSuperPerson method, of class SuperPersonDaoImpl.
     */
    @Test
    public void testAddAndGetSuperPerson() {
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
        
        SuperPerson fromDao = superPersonDao.getSuperPerson(superPerson.getSuperPersonId());
        assertEquals(superPerson, fromDao);
    }

    /**
     * Test of getAllSupers method, of class SuperPersonDaoImpl.
     */
    @Test
    public void testGetAllSupers() {
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
        location2.setName("Jerry's");
        location2.setDescription("Ice  cream shop");
        location2.setLongitude(45.0);
        location2.setLatitude(40.0);
        location2.setStreet("Another street");
        location2.setCity("Another city");
        location2.setZipcode(11232);
        location2.setState("Another State");
        location2 = locationDao.createLocation(location2);
        
        Org organization = new Org();
        organization.setName("Test Name");
        organization.setDescription("Org description");
        organization.setContactInfo("Test info");
        organization.setLocation(location);
        organization = organizationDao.createOrganization(organization);
        
        Org organization2 = new Org();
        organization2.setName("Another Name");
        organization2.setDescription("Another description");
        organization2.setContactInfo("More info");
        organization2.setLocation(location2);
        organization2 = organizationDao.createOrganization(organization2);
        
        List<Org> orgs = new ArrayList<>();
        orgs.add(organization);
        orgs.add(organization2);
          
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
        superPower2.setName("Another Power");
        superPower2.setDescription("Another");
        superPower2 = SuperPowerDao.addSuperPower(superPower2);
        
        SuperPerson superPerson2 = new SuperPerson();
        superPerson2.setSuperpower(superPower2);
        superPerson2.setName("Another Super");
        superPerson2.setDescription("Another");
        superPerson2.setIsVillian(Boolean.getBoolean("Yes"));
        superPerson2.setOrganizations(orgs);
        
        superPerson2 = superPersonDao.addSuperPerson(superPerson2);
        
        List<SuperPerson> supers = superPersonDao.getAllSupers();
        assertEquals(2, supers.size());
        assertTrue(supers.contains(superPerson));
        assertTrue(supers.contains(superPerson2));
        
    }

    /**
     * Test of GetAllMembers method, of class SuperPersonDaoImpl.
     */
    @Test
    public void testGetAllMembers() {
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
        location2.setName("Jerry's");
        location2.setDescription("Ice  cream shop");
        location2.setLongitude(45.0);
        location2.setLatitude(40.0);
        location2.setStreet("Another street");
        location2.setCity("Another city");
        location2.setZipcode(11232);
        location2.setState("Another State");
        location2 = locationDao.createLocation(location2);
       
        Location location3 = new Location();
        location3.setName("Another");
        location3.setDescription("Another");
        location3.setLongitude(45.0);
        location3.setLatitude(40.0);
        location3.setStreet("The street");
        location3.setCity("The city");
        location3.setZipcode(11232);
        location3.setState("The State");
        location3 = locationDao.createLocation(location3);
        
        Org organization = new Org();
        organization.setName("Test Name");
        organization.setDescription("Org description");
        organization.setContactInfo("Test info");
        organization.setLocation(location);
        organization = organizationDao.createOrganization(organization);
        
        Org organization2 = new Org();
        organization2.setName("Another Name");
        organization2.setDescription("Another description");
        organization2.setContactInfo("More info");
        organization2.setLocation(location2);
        organization2 = organizationDao.createOrganization(organization2);
        
        Org organization3 = new Org();
        organization3.setName("Some Name");
        organization3.setDescription("Some description");
        organization3.setContactInfo("Lots of info");
        organization3.setLocation(location3);
        organization3 = organizationDao.createOrganization(organization3);
        
        List<Org> orgs = new ArrayList<>();
        orgs.add(organization);
        orgs.add(organization2);
        
        List<Org> org2 = new ArrayList<>();
        org2.add(organization2);
          
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
        superPower2.setName("Another Power");
        superPower2.setDescription("Another");
        superPower2 = SuperPowerDao.addSuperPower(superPower2);
        
        SuperPerson superPerson2 = new SuperPerson();
        superPerson2.setSuperpower(superPower2);
        superPerson2.setName("Another Super");
        superPerson2.setDescription("Another");
        superPerson2.setIsVillian(Boolean.getBoolean("Yes"));
        superPerson2.setOrganizations(orgs);
        
        superPerson2 = superPersonDao.addSuperPerson(superPerson2);
        
        SuperPower superPower3 = new SuperPower();
        superPower3.setName("Super Power");
        superPower3.setDescription("Super description");
        superPower3 = SuperPowerDao.addSuperPower(superPower3);
        
        SuperPerson superPerson3 = new SuperPerson();
        superPerson3.setSuperpower(superPower3);
        superPerson3.setName("Duper Super");
        superPerson3.setDescription("something");
        superPerson3.setIsVillian(Boolean.getBoolean("Yes"));
        superPerson3.setOrganizations(org2);
        
        List <SuperPerson> orgMembers = superPersonDao.getAllMembers(organization);

        assertEquals(2, orgMembers.size());
        assertTrue(orgMembers.contains(superPerson));
        assertTrue(orgMembers.contains(superPerson2));

    }



    /**
     * Test of updateSuperPerson method, of class SuperPersonDaoImpl.
     */
    @Test
    public void testUpdateSuperPerson() {
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
        
        SuperPerson fromDao = superPersonDao.getSuperPerson(superPerson.getSuperPersonId());
        assertEquals(superPerson, fromDao);
        
        superPerson.setDescription("New description");
        superPersonDao.updateSuperPerson(superPerson);
        
        assertNotEquals(superPerson, fromDao);
        
        fromDao = superPersonDao.getSuperPerson(superPerson.getSuperPersonId());
        
        assertEquals(superPerson, fromDao);
        
    }

    /**
     * Test of deleteSuperPerson method, of class SuperPersonDaoImpl.
     */
    @Test
    public void testDeleteSuperPerson() {
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
        
        SuperPerson fromDao = superPersonDao.getSuperPerson(superPerson.getSuperPersonId());
        
        superOrgDao.deleteSuperPersonOrganization(organization.getOrgId(), superPerson.getSuperPersonId());
        superSightingDao.deleteSuperPersonSighting(sighting.getSightingId(), superPerson.getSuperPersonId());
        superPersonDao.deleteSuperPerson(superPerson.getSuperPersonId());
        
        fromDao = superPersonDao.getSuperPerson(superPerson.getSuperPersonId());
        assertNull(fromDao);
    }
    
}
