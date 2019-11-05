/*
    Not actually integration testing
 */

package com.Brodski.restApi.Integration;

import com.Brodski.restApi.Service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


//For integration tests, use @SpringBootTest https://www.baeldung.com/spring-boot-testing
@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void getAllUsers() {
        System.out.println(userService.getAllUsers());
        System.out.println(userService.getTime());
        System.out.println(userService.getServerPort());
    }

    @Test
    void createUser() {
    }

    @Test
    void testCreate() {
    }

    @Test
    void getTime() {
    }
}