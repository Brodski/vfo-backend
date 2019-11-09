package com.Brodski.restApi.controller;

import com.Brodski.restApi.Service.UserService;
import com.Brodski.restApi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController //Every function below will auto convert the data type that is being returned into JSON
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(){
        String msg = "Hello!!!";
        return msg;
    }

    @GetMapping(value = "/all")
    public List<User> getAll(){
        return userService.getAllUsers();
    }

    @RequestMapping("/create")
    public String create(@RequestParam String username){
        User u = userService.createUser(username);
        return u.toString();
    }

//    @PostMapping(path = "/actualCreate", consumes = "application/x-www-form-urlencoded") //consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE )
 //   public User actualCreate(@RequestParam User newUser){
  //      return userService.createUser(newUser);
   // }

    @PostMapping(path = "/actualCreate") //, consumes = "application/x-www-form-urlencoded") //consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE )
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
    //@PostMapping("/actualCreate")
  //  public @ResponseBody ResponseEntity<String> post() {
//        return new ResponseEntity<String>("POST Response", HttpStatus.OK);
//    }

    @RequestMapping("/create2")
    public String createAux(){
        String t = userService.getTime();
        System.out.println(t);
        User u = userService.createUser(t);
        return u.toString();
    }



}

