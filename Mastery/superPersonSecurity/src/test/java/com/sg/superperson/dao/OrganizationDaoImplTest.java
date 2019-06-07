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
public class OrganizationDaoImplTest {
    
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
    
    public OrganizationDaoImplTest() {
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
     * Test of getOrganization method, of class OrganizationDaoImpl.
     */
    @Test
    public void testCreateAndGetOrganization() {
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
        
        Org organization = new Org();
        organization.setName("Test Name");
        organization.setDescription("Org description");
        organization.setContactInfo("Test info");
        organization.setLocation(location);
        organization = organizationDao.createOrganization(organization);
        
        Org fromDao = organizationDao.getOrganization(organization.getOrgId());
        assertEquals(organization, fromDao);
        
    }

    /**
     * Test of getAllOrganizations method, of class OrganizationDaoImpl.
     */
    @Test
    public void testGetAllOrganizations() {
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
        location2.setDescription("Another test D");
        location2.setLongitude(40.0);
        location2.setLatitude(45.0);
        location2.setStreet("Another test street");
        location2.setCity("Test city");
        location2.setZipcode(11232);
        location2.setState("Test State");
        location2 = locationDao.createLocation(location2);
        
        Org organization = new Org();
        organization.setName("Test Name");
        organization.setDescription("Org description");
        organization.setContactInfo("Test info");
        organization.setLocation(location);
        organization = organizationDao.createOrganization(organization);
        
        Org organization2 = new Org();
        organization2.setName("Another Name");
        organization2.setDescription("Org description");
        organization2.setContactInfo("Test info");
        organization2.setLocation(location2);
        organization2 = organizationDao.createOrganization(organization2);
        
        List<Org> orgs = organizationDao.getAllOrganizations();
        assertEquals(2, orgs.size());
        assertTrue(orgs.contains(organization));
        assertTrue(orgs.contains(organization2));
    }

    /**
     * Test of getAllMembers method, of class OrganizationDaoImpl.
     */
    @Test
    public void getAllOrganizationsOfASuper() {
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
              
        Location location2 = new Location();
        location2.setName("Other test");
        location2.setDescription("Another test D");
        location2.setLongitude(40.0);
        location2.setLatitude(45.0);
        location2.setStreet("Another test street");
        location2.setCity("Test city");
        location2.setZipcode(11232);
        location2.setState("Test State");
        location2 = locationDao.createLocation(location2);
        
        Org organization2 = new Org();
        organization2.setName("Another Name");
        organization2.setDescription("Org description");
        organization2.setContactInfo("Test info");
        organization2.setLocation(location2);
        organization2 = organizationDao.createOrganization(organization2);
        
        List <Org> org = new ArrayList<>();
        org.add(organization);
        org.add(organization2);
        
        SuperPower superPower = new SuperPower();
        superPower.setName("Test Power");
        superPower.setDescription("Power");
        superPower = SuperPowerDao.addSuperPower(superPower);
        
        SuperPerson superPerson = new SuperPerson();
        superPerson.setSuperpower(superPower);
        superPerson.setName("Test Super");
        superPerson.setDescription("Super");
        superPerson.setOrganizations(org);
        superPerson.setIsVillian(Boolean.TRUE);
        superPerson = superPersonDao.addSuperPerson(superPerson);
        
        List<Org> memberOrgs = organizationDao.getAllOrganizationsOfASuper(superPerson);
        assertEquals(2, memberOrgs.size());
        assertTrue(memberOrgs.contains(organization));
        assertTrue(memberOrgs.contains(organization2));
        
    }

    /**
     * Test of deleteOrganization method, of class OrganizationDaoImpl.
     */
    @Test
    public void testDeleteOrganization() {
        
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
        location2.setName("Other test");
        location2.setDescription("Another test D");
        location2.setLongitude(40.0);
        location2.setLatitude(45.0);
        location2.setStreet("Another test street");
        location2.setCity("Test city");
        location2.setZipcode(11232);
        location2.setState("Test State");
        location2 = locationDao.createLocation(location2);
        
        Org organization2 = new Org();
        organization2.setName("Test Name");
        organization2.setDescription("Org description");
        organization2.setContactInfo("Test info");
        organization2.setLocation(location2);
        organization2 = organizationDao.createOrganization(organization2);
        List<Org> org = new ArrayList<>();
        org.add(organization2);
        
        SuperPower superPower = new SuperPower();
        superPower.setName("Test Power");
        superPower.setDescription("Test");
        superPower = SuperPowerDao.addSuperPower(superPower);
        
        SuperPerson superPerson = new SuperPerson();
        superPerson.setSuperpower(superPower);
        superPerson.setName("Test Super");
        superPerson.setDescription("Super");
        superPerson.setIsVillian(Boolean.getBoolean("Yes"));
        superPerson.setOrganizations(org);
        superPerson = superPersonDao.addSuperPerson(superPerson);
        List<SuperPerson> supers = new ArrayList<>();
        supers.add(superPerson);
        
        
        Org fromDao = organizationDao.getOrganization(organization2.getOrgId());
        
        superOrgDao.deleteSuperPersonOrganization(organization2.getOrgId(), superPerson.getSuperPersonId());
        superPersonDao.deleteSuperPerson(superPerson.getSuperPersonId());
        organizationDao.deleteOrganization(organization2.getOrgId());
        
        fromDao = organizationDao.getOrganization(organization2.getOrgId());
        assertNull(fromDao);
    }

    
}
