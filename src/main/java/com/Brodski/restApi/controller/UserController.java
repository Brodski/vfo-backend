package com.Brodski.restApi.controller;

import com.Brodski.restApi.Service.UserService;
import com.Brodski.restApi.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import static com.Brodski.restApi.ActualSecretKeys.DB_ALL;

@CrossOrigin(origins = {"http://localhost:3000",
                        "http://localhost:80",
                        "http://videofeedorganizer.com",
                        "https://videofeedorganizer.com",
                        "http://customyoutube.com",
                        "https://customyoutube.com",
                        "http://vfo.one",
                        "https://vfo.one",
                        })
@RestController
public class UserController {

    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(){
        String msg = "Hello!!!";
        return msg;
    }

//    private User parseData(String idTokenAndUser){
//        JSONObject obj = new JSONObject(idTokenAndUser);
//        JSONObject userFromClientJson = obj.getJSONObject("user");
//        String idTokenString = obj.getString("idtoken");
//        Gson gson = new Gson();
//        User userFromClient = gson.fromJson(String.valueOf(userFromClientJson), User.class);
//        return userFromClient;
//    }

    @PostMapping("user/delete")
    public ResponseEntity<User> deleteUser(@RequestBody String idTokenAndUser) throws GeneralSecurityException, IOException {
        JSONObject obj = new JSONObject(idTokenAndUser);
        JSONObject userFromClientJson = obj.getJSONObject("user");
        String idTokenString = obj.getString("idtoken");
        Gson gson = new Gson();
        User userFromClient = gson.fromJson(String.valueOf(userFromClientJson), User.class);

        boolean isPass = userService.deleteUser(idTokenString, userFromClient);
        if (isPass) {
            log.info("User delete successful!");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        log.info("User delete unsuccessful :(");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("user/save")
    public ResponseEntity<User> saveUser(@RequestBody String idTokenAndUser) throws GeneralSecurityException, IOException {

        JSONObject obj = new JSONObject(idTokenAndUser);
        JSONObject userFromClientJson = obj.getJSONObject("user");
        String idTokenString = obj.getString("idtoken");
        Gson gson = new Gson();
        User userFromClient = gson.fromJson(String.valueOf(userFromClientJson), User.class);

        User u = userService.saveUser(idTokenString, userFromClient);
        if (u != null) {
            log.info("User save successful!");
            return new ResponseEntity<User>(u, HttpStatus.OK);
        }
        log.info("User save unauthorized");
        return new ResponseEntity<User>(u, HttpStatus.UNAUTHORIZED);

    }

    @PostMapping("user/login")
    public ResponseEntity<User> loginUser(@RequestBody String idToken) throws GeneralSecurityException, IOException {
        User user = userService.loginUser(idToken);
        if (user != null) {
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        log.info("User login unauthorized!");
        return new ResponseEntity<User>(user, HttpStatus.UNAUTHORIZED);
    }

    // There is not any sensitive data in the database. This is safe. Worst case, it's exposed yet it is still safe.
    //
    // I know that this is not secure! It's bad. (no sensitive data though, it's lazy admin info)
    @RequestMapping(value = "/all")
    public List<User> getAll(@RequestParam(value="secret") String db){
        if (db.equals(DB_ALL)){
            log.info("/all accessed!");
            return userService.getAllUsers();
        }
        else {
            log.info("/all deined");
            List<User> idk = new ArrayList<>();
            return idk;
        }

    }
}

