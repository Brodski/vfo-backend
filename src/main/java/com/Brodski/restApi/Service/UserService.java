package com.Brodski.restApi.Service;

import com.Brodski.restApi.UserRepository.UserRepository;
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

    public void loginUser(String id_JsonString) throws GeneralSecurityException, IOException {
        String idtoken = processIdString(id_JsonString);
        User user = validateIdToken(idtoken);
        System.out.println("CREATE USER: validateIdToken");
        System.out.println(user);
        if (user != null){
            System.out.println("CREATE USER: USER IS VALID");
            //Check database
            User userInDb = getByGoogleId(user.googleId);
            System.out.println("User after database get");
            System.out.println(userInDb);
            if (userInDb == null){
                System.out.println("CREATING NEW USER! (user != null) ");
                userRepo.save(user);
            }
            else {
                System.out.println("CREATE USER: USER ALREADY EXISTS!!");
            }
        }
        return;
    }

    private String processIdString(String id_JsonString){

        Gson gson = new Gson();
        JSONObject obj = new JSONObject(id_JsonString);
        String idTokenString = obj.getString("idtoken");
        System.out.println("obj.getString(idtoken)");
        System.out.println(obj.getString("idtoken"));
        return idTokenString;

    }

    public User getUser(String id_JsonString) throws GeneralSecurityException, IOException {
        User user = null;
        String idtoken = processIdString(id_JsonString);
        user = validateIdToken(idtoken);
        System.out.println("isValid? " + (user != null));
        if (user != null){
            user = getByGoogleId(id_JsonString);
        }
        return user;
    }



    // oauth library
    // GUIDE: https://developers.google.com/identity/sign-in/web/listeners
    // API: https://googleapis.dev/java/google-api-services-oauth2/latest/index.html
                // jackson is better??? to research https://blog.overops.com/the-ultimate-json-library-json-simple-vs-gson-vs-jackson-vs-json/
                 //https://stackoverflow.com/questions/2591098/how-to-parse-json-in-java
    private User validateIdToken(String idTokenString) throws GeneralSecurityException, IOException {
        boolean isValid = false;
        System.out.println(APIKEY);
        System.out.println(CLIENT_ID);
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
            user.googleId   = userId;
            user.pictureUrl = (String) payload.get("picture");
            user.username   = (String) payload.get("name");

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
            System.out.println("User ID: " + locale);
            System.out.println("User ID: " + familyName);
            System.out.println("User ID: " + givenName);
            isValid = true;

        } else {
            System.out.println("Invalid ID token.");
            isValid = false;
        }
        return user;
    }

    public User getByGoogleId(String googleId){
        return userRepo.findByGoogleId(googleId)   ;

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
