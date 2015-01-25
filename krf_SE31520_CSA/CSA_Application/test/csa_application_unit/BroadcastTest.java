/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csa_application_unit;

import csa_application.data.Broadcast;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kit
 */
public class BroadcastTest {
    
    Broadcast testBroadcast;
    
    @Before
    public void setUp() {
       testBroadcast  = new Broadcast();
    }

    /**
     * Test of getUrl and setURL method, of class Broadcast.
     */
    @Test
    public void testSetandGetUrl() {
        System.out.println("setUrl and getUrl");
        String expResult = "http://localhost:3000/users";
        testBroadcast.setUrl(expResult);
        assertEquals(expResult, testBroadcast.getUrl());
    }

    /**
     * Test of setContent and getContent method, of class Broadcast.
     */
    @Test
    public void testSetandGetContent() {
        System.out.println("setContent and getContent");
        String expResult = "Hello!";
        testBroadcast.setContent(expResult);
        assertEquals(expResult, testBroadcast.getContent());
    }

    /**
     * Test of setUserId and getUserId method, of class Broadcast.
     */
    @Test
    public void testSetandGetUserId() {
        System.out.println("setUserId and getUserId");
        Integer expResult = 44;
        testBroadcast.setUserId(expResult);
        assertEquals(expResult, testBroadcast.getUserId());
    }

}
