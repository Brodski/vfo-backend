package com.Brodski.restApi.controller;

import com.Brodski.restApi.Service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

//For integration tests, use @SpringBootTest https://www.baeldung.com/spring-boot-testing
@RunWith(SpringRunner.class)
@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserService userServiceTest;

    @Test
    void index() {
        System.out.println(userServiceTest.getAllUsers());
        System.out.println(userServiceTest.getTime());
    }

    @Test
    void getAll() {
    }

    @Test
    void create() {
    }

    @Test
    void createAux() {
    }
}