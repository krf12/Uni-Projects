/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csa_application_unit;

import csa_application.data.Broadcast;
import csa_application.data.RestletUtility;
import csa_application.data.User;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.restlet.resource.ClientResource;

/**
 *
 * @author Kit
 */
public class RestletUtilityTest {
    
    RestletUtility ru;
    ClientResource cr;
    ArrayList<Broadcast> testBroadcasts;
    ArrayList<User> testUsers;
    private final String BASIC_URI = "http://localhost:3000";
    
    @Before
    public void setUp() {
        ru = new RestletUtility();
         cr = new ClientResource("");
         testBroadcasts = new ArrayList<>();
         testUsers = new ArrayList<>();
    }
    
     /**
     * Test of authenticate method, of class RestletUtility.
     */

    @Test
    public void testFailingAuthentication() {
        System.out.println("Where authenticate should be false");
        String username = "krf";
        String password = "secret";
        
        Boolean check = ru.authenticate(username, password, cr);
        
        assertEquals(false, check);
    }
    
    /**
     * Test of authenticate method, of class RestletUtility.
     */
    
    @Test
    public void testSuccessfulAuthentication() {
        System.out.println("Where authenticate should be true");
        String username = "admin";
        String password = "taliesin";
        
        Boolean check = ru.authenticate(username, password, cr);
        
        assertEquals(true, check);
    }
    
    @Test
    public void testConnectUsers(){
        System.out.println("connectUsers");
        
        String username = "admin";
        String password = "taliesin";
        
        Boolean check = ru.authenticate(username, password, cr);
        
        testUsers = ru.connectUsers(cr, BASIC_URI);
        Boolean empty = testUsers.isEmpty();
        
        assertEquals(false, empty);
        
    }
    
    @Test
    public void testConnectBroadcasts(){
        System.out.println("connectBroadcasts");
        
        String username = "admin";
        String password = "taliesin";
        
        Boolean check = ru.authenticate(username, password, cr);
        
        testBroadcasts = ru.connectBroadcasts(cr, BASIC_URI);
        Boolean empty = testBroadcasts.isEmpty();
        
        assertEquals(false, empty);
        
    }
    
    @Test
    public void testNewBroadcast(){
        System.out.println("newBroadcast");
        
        String username = "admin";
        String password = "taliesin";
        
        ru.authenticate(username, password, cr);
        
        Broadcast temp = new Broadcast();
        temp.setContent("Testing");
        int twitterInt = 1;
        int emailInt = 0;
        
        Boolean contains = false;
        
        ru.newBroadcast(temp, twitterInt, emailInt, cr, BASIC_URI);
        
        testBroadcasts = ru.connectBroadcasts(cr, BASIC_URI);
        
        for(int i = 0; i < testBroadcasts.size(); i++){
            if(testBroadcasts.get(i).getContent().equals(temp.getContent())){
                contains = true;
            }
        }
        
        assertEquals(true, contains);
        
    }
    
    @Test
    public void testNewUser(){
        System.out.println("newUser");
        
        String username = "admin";
        String password = "taliesin";
        
        Boolean check = ru.authenticate(username, password, cr);
        
        String tempLogin = "krf";
        String tempPassword = "test";
        
        User temp = new User();
        temp.setEmail("krf@aber.ac.uk");
        temp.setFirstName("Kit");
        temp.setGrad_year(1985);
        temp.setJobs(true);
        temp.setPhone("01256789304");
        temp.setSurname("Farmer");
        int jobInt = (temp.getJobs()) ? 1 : 0;
        
        Boolean contains = false;
        
        ru.newUser(tempLogin, tempPassword, temp, jobInt, cr, BASIC_URI);
        
        testUsers = ru.connectUsers(cr, BASIC_URI);
        
        for(int i = 0; i < testUsers.size(); i++){
            if(testUsers.get(i).getEmail().equals(temp.getEmail())){
                contains = true;
            }
        }
        
        assertEquals(true, contains);
        
    }
    
    @Test
    public void testEditUser(){
        System.out.println("editUser");
        
        String username = "admin";
        String password = "taliesin";
        
        ru.authenticate(username, password, cr);
        
        testUsers = ru.connectUsers(cr, BASIC_URI);
        User temp = new User();
        
        for(int i = 0; i < testUsers.size(); i++){
            if(testUsers.get(i).getEmail().equals("krf@aber.ac.uk")){
                temp = testUsers.get(i);
            }
        }
        
       temp.setGrad_year(2000);
       int jobInt = (temp.getJobs()) ? 1 : 0;
       
       ru.editUser(temp, jobInt, cr, BASIC_URI);
       
       testUsers = ru.connectUsers(cr, BASIC_URI);
       
       Boolean check = false;
       
        for(int i = 0; i < testUsers.size(); i++){
            if(testUsers.get(i).getEmail().equals("krf@aber.ac.uk")){
                if(testUsers.get(i).getGrad_year() == 2000){
                    check = true;
                }
            }
        }
       
        assertEquals(true, check);
        
    }
    
    
}
