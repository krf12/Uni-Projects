/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csa_application_unit;

import csa_application.data.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kit
 */
public class UserTest {
    
    User testUser;
    
    @Before
    public void setUp() {
        testUser = new User();
    }

    /**
     * Test of setUrl and getUrl method, of class User.
     */
    @Test
    public void testSetandGetUrl() {
        System.out.println("setUrl and getUrl");
        String expResult = "http://localhost::3000/users";
        testUser.setUrl(expResult);
        assertEquals(expResult, testUser.getUrl());
    }

    /**
     * Test of setSurname and getSurname method, of class User.
     */
    @Test
    public void testSetandGetSurname() {
        System.out.println("setSurname and getSurname");
        String expResult = "Farmer";
        testUser.setSurname(expResult);
        assertEquals(expResult, testUser.getSurname());
    }

    /**
     * Test of setFirstName and getFirstName method, of class User.
     */
    @Test
    public void testSetandGetFirstName() {
        System.out.println("setFirstName and getFirstName");
        String expResult = "Kit";
        testUser.setFirstName(expResult);
        assertEquals(expResult, testUser.getFirstName());
    }

    /**
     * Test of setPhone and getPhone method, of class User.
     */
    @Test
    public void testSetandGetPhone() {
        System.out.println("setPhone and getPhone");
        String expResult = "01256456789";
        testUser.setPhone(expResult);
        assertEquals(expResult, testUser.getPhone());
    }

    /**
     * Test of setGrad_year and getGrad_year method, of class User.
     */
    @Test
    public void testSetandGetGrad_year() {
        System.out.println("setGrad_year And getGrad_year");
        Integer expResult = 1985;
        testUser.setGrad_year(expResult);
        assertEquals(expResult, testUser.getGrad_year());
    }

    /**
     * Test of setJobs and getJobs method, of class User.
     */
    @Test
    public void testSetandGetJobs() {
        System.out.println("setJobs and getJobs");
        Boolean expResult = true;
        testUser.setJobs(expResult);
        assertEquals(expResult, testUser.getJobs());
    }

    /**
     * Test of setEmail and getEmail method, of class User.
     */
    @Test
    public void testSetAndGetEmail() {
        System.out.println("setEmail and getEmail");
        String expResult = "krf@aber.ac.uk";
        testUser.setEmail(expResult);
        assertEquals(expResult, testUser.getEmail());
    }

}
