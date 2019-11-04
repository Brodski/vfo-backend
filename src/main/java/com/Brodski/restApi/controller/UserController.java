package com.Brodski.restApi.controller;

import com.Brodski.restApi.Service.UserService;
import com.Brodski.restApi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(){
        String msg = "Hello!!!";
        return msg;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<User> getAll(){
        return userService.getAllUsers();
    }

    @RequestMapping("/create")
    public String create(@RequestParam String username){
        User u = userService.createUser(username);
        return u.toString();
    }

    @RequestMapping("/create2")
    public String createAux(){
        String t = userService.getTime();
        System.out.println(t);
        User u = userService.createUser(t);
        return u.toString();
    }



}

