package com.Brodski.restApi.Service;

import com.Brodski.restApi.UserRepository.UserRepository;
import com.Brodski.restApi.model.User;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.Gson;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
//import com.Brodski.restApi.com.Brodski.restApi.SecretKeys.
//import com.Brodski.restApi.SomeBullshit;
//import static com.Brodski.restApi.
import static com.Brodski.restApi.SecretKeys.CLIENT_ID;
import static com.Brodski.restApi.SecretKeys.APIKEY;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;


//TODO:
// Research how to secure apikey

@Service
public class UserService {

//    @Autowired private UserRepository userRepo;
    private final UserRepository userRepo;

    public UserService(UserRepository userRepository){
        this.userRepo = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public User createUser(String username ){
        return userRepo.save(new User(username));
    }

    public User createUser(User user ){
        return userRepo.save(user);
    }

    public User getByUsername(String username){
        return userRepo.findByUsername(username);
    }

    public User getByGoogleId(String googleId){
        return userRepo.findByGoogleId(googleId)   ;

    }

    public String getTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd, HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String s = dtf.format(now);
        return s;
    }

    public User getUser(String id_JsonString) throws GeneralSecurityException, IOException {
        User user = new User();
        Gson gson = new Gson();
        JSONObject obj = new JSONObject(id_JsonString);
        System.out.println("obj.getString(idtoken)");
        System.out.println(obj.getString("idtoken"));
        String idTokenString = obj.getString("idtoken");

        boolean isValid = validateIdToken(idTokenString);
        System.out.println("isValid");
        System.out.println(isValid);
        if (isValid){
            System.out.println("VALID");
            user = getByGoogleId(id_JsonString);
            System.out.println("google user");
            System.out.println(user);
        }
        else {
            System.out.println("NOT VALID");
        }
        return user;



    }

    // oauth library
    // GUIDE: https://developers.google.com/identity/sign-in/web/listeners
    // API: https://googleapis.dev/java/google-api-services-oauth2/latest/index.html
                // jackson is better??? to research https://blog.overops.com/the-ultimate-json-library-json-simple-vs-gson-vs-jackson-vs-json/
                 //https://stackoverflow.com/questions/2591098/how-to-parse-json-in-java
    private boolean validateIdToken(String idTokenString) throws GeneralSecurityException, IOException {
        boolean isValid = false;
        System.out.println(APIKEY);
        System.out.println(CLIENT_ID);

        NetHttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
        JacksonFactory jacksonFactory = new JacksonFactory();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(CLIENT_ID))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        // (Receive idTokenString by HTTPS POST)

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();

            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            System.out.println("User ID: " + userId);
            System.out.println("User ID: " + email);
            System.out.println("User ID: " + emailVerified);
            System.out.println("User ID: " + userId);
            System.out.println("User ID: " + name);
            System.out.println("User ID: " + pictureUrl);
            System.out.println("User ID: " + locale);
            System.out.println("User ID: " + familyName);
            System.out.println("User ID: " + givenName);
            isValid = true;

        } else {
            System.out.println("Invalid ID token.");
            isValid = false;
        }
        return isValid;
    }
}
