package com.Brodski.restApi.Service;

import com.Brodski.restApi.UserRepository.UserRepository;
import com.Brodski.restApi.controller.UserController;
import com.Brodski.restApi.model.User;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static com.Brodski.restApi.ActualSecretKeys.CLIENT_ID; // Constant interface - https://en.wikipedia.org/wiki/Constant_interface  https://stackoverflow.com/questions/2659593/what-is-the-use-of-interface-constants

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.jackson2.JacksonFactory;



//TODO:  Research how to secure apikey

@Service
public class UserService {

    private Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepo;

    public UserService(UserRepository userRepository){
        this.userRepo = userRepository;
    }

    public boolean deleteUser(String idToken, User userReq) throws GeneralSecurityException, IOException {
        User user = validateIdToken(idToken);
        log.info("Deleting");
        log.info(user.googleId);
        if (user != null) {
            // if the googleId doesn't exist in database then that means "deleting", ie removing user data from db, is a success
            userRepo.deleteUserByGoogleId(user.googleId);
            return true;
        }
        return false;
    }

    public User saveUser(String idToken, User userReq) throws GeneralSecurityException, IOException {
        User user = validateIdToken(idToken);
        userReq.pictureUrl = user.pictureUrl;
        userReq.googleId = user.googleId;
        if (user != null){
            User userInDb = userRepo.findByGoogleId(userReq.googleId);
            userInDb.setCustomShelfs(userReq.customShelfs);
            userInDb.setPictureUrl(userReq.pictureUrl);
            userInDb.setUsername(userReq.username);
            user = userRepo.save(userInDb);
        }
        return user;
    }

    public User loginUser(String id_JsonString) throws GeneralSecurityException, IOException {
        String idtoken = processIdString(id_JsonString);
        User user = validateIdToken(idtoken);
        if (user != null){
            User userInDb = userRepo.findByGoogleId(user.googleId);
            if (userInDb == null){
                userRepo.save(user);
            }
            else {
                user = userInDb;
            }
        }
        return user;
    }

    private String processIdString(String id_JsonString){
        JSONObject obj = new JSONObject(id_JsonString);
        String idTokenString = obj.getString("idtoken");
        return idTokenString;
    }

    // oauth library
    // GUIDE: https://developers.google.com/identity/sign-in/web/backend-auth
    // DOCS: https://googleapis.dev/java/google-api-services-oauth2/latest/index.html
    // JSON stuff https://stackoverflow.com/questions/2591098/how-to-parse-json-in-java
    private User validateIdToken(String idTokenString) throws GeneralSecurityException, IOException {

        User user = null;
        NetHttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
        JacksonFactory jacksonFactory = new JacksonFactory();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        // (Receive idTokenString by HTTPS POST)
        GoogleIdToken idToken = verifier.verify(idTokenString); 
        if (idToken != null) {
            Payload payload = idToken.getPayload();

            String userId = payload.getSubject();
            user = new User();
            user.setGoogleId(userId);
            user.setPictureUrl( (String) payload.get("picture"));
            user.setUsername( (String) payload.get("name"));

            String name             = (String) payload.get("name");
            log.info("User " + name + " verified by Google Auth.");

        } else {
            log.info("Invalid ID token");
        }
        return user;
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public String getTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd, HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String s = dtf.format(now);
        return s;
    }
}
