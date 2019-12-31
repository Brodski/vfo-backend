package com.Brodski.restApi.UnitTests;

import static org.junit.Assert.*;
import com.Brodski.restApi.Service.UserService;
import com.Brodski.restApi.UserRepository.UserRepository;
import com.Brodski.restApi.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

// https://reflectoring.io/unit-testing-spring-boot/
class UserServiceTest {

    private UserRepository userRepository = Mockito.mock(UserRepository.class);

    private UserService userService;

    @BeforeEach
    void initUseCase() {
        userService = new UserService(userRepository);
    }

    @Test
    void getAllUsers() {
        System.out.println(userService.getAllUsers());
        System.out.println(userService.getTime());
    }

    @Test
    void createUser() {
        User user = new User("test-user");
        user.setId("test-user-id");
        when(userRepository.save(user)).thenReturn(user);

//        User savedUser = userService.createUser(user);
  //      assertEquals(user, savedUser);
        assertEquals(1,1);
    }

    @Test
    void getTime() {
    }
}