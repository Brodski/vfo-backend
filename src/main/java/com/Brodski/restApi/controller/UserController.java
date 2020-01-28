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
import java.util.List;



@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:80", "http://betteryoutube.dns-cloud.net","http://betteryoutube.dns-cloud.net:80"})
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

    @GetMapping(value = "/all")
    public List<User> getAll(){
        return userService.getAllUsers();
    }
}

