package com.Brodski.restApi.controller;

import com.Brodski.restApi.Service.UserService;
import com.Brodski.restApi.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
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
        User u = userService.createUser(newUser);
        System.out.println(u);
        return u;
    }

    @PostMapping("/user/create")
    public String createUser(@RequestBody String idToken) throws GeneralSecurityException, IOException {
        System.out.println("\nGOT IT! " + idToken);
        System.out.println("\n");
        User user = userService.getUser(idToken);
        System.out.println("create user is null?");
        System.out.println(user);
        // if user is null
        

        return "SENDING IT!";
    }


    @PostMapping("/user/authenticate")
    public String authenticateUser(@RequestBody String idToken) throws GeneralSecurityException, IOException {
        System.out.println("\nGOT IT! " + idToken);
        System.out.println("\n");
        userService.getUser(idToken);

        return "SENDING IT!";

    }

    @PostMapping(path = "/userDebug") //, consumes = "application/x-www-form-urlencoded") //consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE )
    public ResponseEntity<String> handleRequest (RequestEntity<String> requestEntity) {

        HttpHeaders headers = requestEntity.getHeaders();
        HttpMethod method = requestEntity.getMethod();

        System.out.println("request body : " + requestEntity.getBody());
        System.out.println("request headers : " + headers);
        System.out.println("request method : " + method);
        System.out.println("request url: " + requestEntity.getUrl());

        ResponseEntity<String> responseEntity = new ResponseEntity<>("my response body",
                HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping("/create2")
    public String createAux(){
        String t = userService.getTime();
        System.out.println(t);
        User u = userService.createUser(t);
        return u.toString();
    }



}

