package com.Brodski.restApi.Service;

import com.Brodski.restApi.UserRepository.UserRepository;
import com.Brodski.restApi.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserRepository userRepository = Mockito.mock(UserRepository.class);

    private UserService userServiceTest;

    @BeforeEach
    void initUseCase() {
        //userServiceTest = new UserService(userRepository);
    }

    @Test
    void getAllUsers() {
        System.out.println(userServiceTest.getAllUsers());
        System.out.println(userServiceTest.getTime());
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