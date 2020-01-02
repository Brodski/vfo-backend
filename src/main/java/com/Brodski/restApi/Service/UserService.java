package com.Brodski.restApi.Service;

import com.Brodski.restApi.UserRepository.UserRepository;
import com.Brodski.restApi.model.CustomShelf;
import com.Brodski.restApi.model.User;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.Gson;
import org.json.JSONObject;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
//import com.Brodski.restApi.com.Brodski.restApi.SecretKeyz.
//import com.Brodski.restApi.SomeBullshit;
//import static com.Brodski.restApi.
import static com.Brodski.restApi.SecretKeyz.CLIENT_ID;
import static com.Brodski.restApi.SecretKeyz.APIKEY;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.jackson2.JacksonFactory;


//TODO:
// Research how to secure apikey

@Service
public class UserService {

//    @Autowired private UserRepository userRepo;
    private final UserRepository userRepo;

    public UserService(UserRepository userRepository){
        this.userRepo = userRepository;
    }

    public User saveUser(String idToken, User userReq) throws GeneralSecurityException, IOException {
        System.out.println("INSIDE OF SAVEUSER SEVERICE");

        User user = validateIdToken(idToken);

        userReq.pictureUrl = user.pictureUrl;
        userReq.googleId = user.googleId;
        if (user != null){
            User userInDb = userRepo.findByGoogleId(userReq.googleId);
            System.out.println("PRE userInDb");
            System.out.println(userInDb);
            userInDb.setCustomShelfs(userReq.customShelfs);
            userInDb.setPictureUrl(userReq.pictureUrl);
            userInDb.setUsername(userReq.username);
            System.out.println("POST userInDb");
            System.out.println(userInDb);
            user = userRepo.save(userInDb);
        }
        return user;

    }

    public User loginUser(String id_JsonString) throws GeneralSecurityException, IOException {
        boolean isValid = false;
        String idtoken = processIdString(id_JsonString);
        User user = validateIdToken(idtoken);
        System.out.println("loginUser :");
        System.out.println("user");
        System.out.println(user);
        if (user != null){
            System.out.println("loginUser: USER IS VALID");
            isValid = true;
            //Check database
            User userInDb = userRepo.findByGoogleId(user.googleId);
            System.out.println("loginUser:  User after database get");
            System.out.println(userInDb);
            if (userInDb == null){
                System.out.println("loginUser:  CREATING NEW USER! (user == null) ");
                //user.customShelfs = new CustomShelf[]
                userRepo.save(user);
            }
            else {
                System.out.println("loginUser:  CREATE USER: USER ALREADY EXISTS!!");
                user = userInDb;
            }
        }
        return user;
    }

    private String processIdString(String id_JsonString){

        Gson gson = new Gson();
        JSONObject obj = new JSONObject(id_JsonString);
        String idTokenString = obj.getString("idtoken");
//        System.out.println("obj.getString(idtoken) :\n" + obj.getString("idtoken"));
        return idTokenString;

    }


    // oauth library
    // GUIDE: https://developers.google.com/identity/sign-in/web/listeners
    // API: https://googleapis.dev/java/google-api-services-oauth2/latest/index.html
                // jackson is better??? to research https://blog.overops.com/the-ultimate-json-library-json-simple-vs-gson-vs-jackson-vs-json/
                 //https://stackoverflow.com/questions/2591098/how-to-parse-json-in-java
    private User validateIdToken(String idTokenString) throws GeneralSecurityException, IOException {
        boolean isValid = false;
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
           // user.googleId   = userId;
           // user.pictureUrl = (String) payload.get("picture");
           // user.username   = (String) payload.get("name");
            user.setGoogleId(userId);
            user.setPictureUrl( (String) payload.get("picture"));
            user.setUsername( (String) payload.get("name"));

            String email            = payload.getEmail();
            boolean emailVerified   = Boolean.valueOf(payload.getEmailVerified());
            String name             = (String) payload.get("name");
            String pictureUrl       = (String) payload.get("picture");
            String locale           = (String) payload.get("locale");
            String familyName       = (String) payload.get("family_name");
            String givenName        = (String) payload.get("given_name");

            System.out.println("User ID: " + userId);
            System.out.println("User ID: " + email);
            System.out.println("User ID: " + emailVerified);
            System.out.println("User ID: " + userId);
            System.out.println("User ID: " + name);
            System.out.println("User ID: " + pictureUrl);

        } else {
            System.out.println("Invalid ID token. idToken:");
            System.out.println(idToken);
        }
        return user;
    }


////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    /*public User createUser(String username ){
        return userRepo.save(new User(username));
    }*/

    /*public User createUser(User user ){
        return userRepo.save(user);
    }*/

    public User getByUsername(String username){
        return userRepo.findByUsername(username);
    }


    public String getTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd, HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String s = dtf.format(now);
        return s;
    }


}
