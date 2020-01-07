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


import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:80"})
@RestController //Every function below will auto convert the data type that is being returned into JSON  //consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE )
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
        JSONObject uuu = obj.getJSONObject("user");
        String idTokenString = obj.getString("idtoken");
        Gson gson = new Gson();
        User user = gson.fromJson(String.valueOf(uuu), User.class);

        User u = userService.saveUser(idTokenString, user);
        if (u != null) {
            System.out.println("\nGrats, you're in");
            return new ResponseEntity<User>(u, HttpStatus.OK);
        }
        return new ResponseEntity<User>(u, HttpStatus.UNAUTHORIZED);

    }

    @PostMapping("user/login")
    public ResponseEntity<User> loginUser(@RequestBody String idToken) throws GeneralSecurityException, IOException {
        System.out.println("\nLoggin in user...");
        User user = userService.loginUser(idToken);
        if (user != null) {
            System.out.println("\nGrats, you're in");
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        return new ResponseEntity<User>(user, HttpStatus.UNAUTHORIZED);
    }

    ///////////////////////////////////////////////////////////////////////////



    @GetMapping(value = "/all")
    public List<User> getAll(){
        log.info("!!!! Hello 222");
        return userService.getAllUsers();
    }

    //if user.id==null or not null, the magic of Spring will create an ID for us
    @RequestMapping("/createUser")
    public User createUser(@RequestBody User newUser){
        System.out.println("-------");
        System.out.println(newUser);
        //User u = userService.createUser(newUser);
     //   System.out.println(u);
        return new User();
    }


    @PostMapping(path = "/userDebug") //, consumes = "application/x-www-form-urlencoded") //consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE )
    //public ResponseEntity<String> handleRequest (RequestEntity<String> requestEntity) {
    public ResponseEntity<User> handleRequest (RequestEntity<User> requestEntity) {

        HttpHeaders headers = requestEntity.getHeaders();
        HttpMethod method = requestEntity.getMethod();

        System.out.println("request body : " + requestEntity.getBody());
        System.out.println("request headers : " + headers);
        System.out.println("request method : " + method);
        System.out.println("request url: " + requestEntity.getUrl());

        ResponseEntity<User> responseEntity = new ResponseEntity<>(new User(),
                HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping("/create2")
    public String createAux(){
        String t = userService.getTime();
        System.out.println(t);
        //User u = userService.createUser(t);
        return t;
    }



}

