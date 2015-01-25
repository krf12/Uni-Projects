/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csa_application.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

/**
 *
 * @author Kit
 */
public class RestletUtility {
    
    public Boolean authenticate(String userName, String password, ClientResource cr){
        cr.setReference("http://localhost:3000/users");
        ChallengeScheme scheme = ChallengeScheme.HTTP_BASIC;
        ChallengeResponse authentication = new ChallengeResponse(scheme,
                userName, password);
        cr.setChallengeResponse(authentication);
        return cr.handle().getLocationRef().equals(cr.getReference());
    }
    
    public ArrayList<User> connectUsers(ClientResource cr, String BASIC_URI){
        ArrayList<User> tempUsers = new ArrayList<>();
        try {       
            cr.setReference(BASIC_URI + "/users/");
            String jsonText = cr.get(MediaType.APPLICATION_JSON).getText();
            JsonElement jelement = new JsonParser().parse(jsonText);
            JsonArray jArray = jelement.getAsJsonArray();
            for (int i = 0; i < jArray.size(); i++) {
                JsonObject temp = jArray.get(i).getAsJsonObject();
                User tempUser = new User();
                tempUser.setSurname(temp.get("surname").getAsString());
                tempUser.setFirstName(temp.get("firstname").getAsString());
                tempUser.setPhone(temp.get("phone").getAsString());
                tempUser.setGrad_year(temp.get("grad_year").getAsInt());
                tempUser.setEmail(temp.get("email").getAsString());
                tempUser.setJobs(temp.get("jobs").getAsBoolean());
                tempUser.setUrl(temp.get("url").getAsString());
                tempUsers.add(tempUser);
            }
        } catch (IOException ex) {
            Logger.getLogger(RestletUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tempUsers;
    }
    
    public ArrayList<Broadcast> connectBroadcasts(ClientResource cr, String BASIC_URI){
        ArrayList<Broadcast> tempBroadcasts = new ArrayList<>();
        try {
            cr.setReference(BASIC_URI + "/broadcasts");

            String jsonText = cr.get(MediaType.APPLICATION_JSON).getText();
            JsonElement jelement = new JsonParser().parse(jsonText);

            JsonArray jArray = jelement.getAsJsonArray();
            for (int i = 0; i < jArray.size(); i++) {
                JsonObject temp = jArray.get(i).getAsJsonObject();
                Broadcast tempBroadcast = new Broadcast();
                tempBroadcast.setContent(temp.get("content").getAsString());
                tempBroadcast.setUserId(temp.get("user_id").getAsInt());
                tempBroadcast.setUrl(temp.get("url").getAsString());
                tempBroadcasts.add(tempBroadcast);
            }
        } catch (IOException ex) {
            Logger.getLogger(RestletUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tempBroadcasts;
    }
    
    public void newBroadcast(Broadcast temp, int twitterInt, int emailInt, ClientResource cr, String BASIC_URI){
        JsonObject jContent = new JsonObject();
            jContent.addProperty("content", temp.getContent());
            JsonObject jSent = new JsonObject();
            if(emailInt != 0){
            jSent.addProperty("email", emailInt);
            }
            if(twitterInt != 0){
            jSent.addProperty("twitter", twitterInt);
            }
            JsonObject jBroadcast = new JsonObject();
            jBroadcast.add("broadcast", jContent);
            jBroadcast.add("feeds", jSent);
            Representation rep = new JsonRepresentation(jBroadcast.toString());
            rep.setMediaType(MediaType.APPLICATION_JSON);
            cr.setReference(BASIC_URI + "/broadcasts");
            cr.post(rep);
    }
    
    public void editUser(User temp, int jobInt, ClientResource cr, String BASIC_URI){
        JsonObject jRequest = new JsonObject();
                jRequest.addProperty("firstname", temp.getFirstName());
                jRequest.addProperty("phone", temp.getPhone());
                jRequest.addProperty("surname", temp.getSurname());
                jRequest.addProperty("jobs", "" + jobInt + "");
                jRequest.addProperty("grad_year", "" + temp.getGrad_year() + "");
                jRequest.addProperty("email", temp.getEmail());

                JsonObject js = new JsonObject();
                js.add("user", jRequest);
                Representation rep = new JsonRepresentation(js.toString());
                rep.setMediaType(MediaType.APPLICATION_JSON);
                cr.setReference(temp.getUrl());
                cr.put(rep);
    }
    
    public void newUser(String tempLogin, String tempPassword, User temp, int jobInt, ClientResource cr, String BASIC_URI){
        JsonObject jUser = new JsonObject();
            jUser.addProperty("login", tempLogin);
            jUser.addProperty("password", tempPassword);
            jUser.addProperty("password_confirmation", tempPassword);
            JsonObject jRequest = new JsonObject();
            jRequest.addProperty("firstname", temp.getFirstName());
            jRequest.addProperty("phone", temp.getPhone());
            jRequest.addProperty("surname", temp.getSurname());
            jRequest.addProperty("jobs", "" + jobInt + "");
            jRequest.addProperty("grad_year", "" + temp.getGrad_year() + "");
            jRequest.addProperty("email", temp.getEmail());
            jRequest.add("user_detail_attributes", jUser);
            JsonObject js = new JsonObject();
            js.add("user", jRequest);
            Representation rep = new JsonRepresentation(js.toString());
            rep.setMediaType(MediaType.APPLICATION_JSON);
            cr.setReference(BASIC_URI + "/users");
            cr.post(rep);
    }
}
