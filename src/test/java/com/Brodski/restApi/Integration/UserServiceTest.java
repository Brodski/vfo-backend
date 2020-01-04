/*
    Not actually integration testing
 */

package com.Brodski.restApi.Integration;

import static org.junit.Assert.*;
import com.Brodski.restApi.AppProperties;
import com.Brodski.restApi.Service.UserService;
import com.Brodski.restApi.UserRepository.UserRepository;
import com.Brodski.restApi.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


//For integration tests, use @SpringBootTest https://www.baeldung.com/spring-boot-testing
@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS) //Allows @BeforeAll to be NOT be static
class UserServiceTest {

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    final private String db_integration = "YtUsers";
    final private String host_integration ="localhost";

    @BeforeAll
    public void init(){
        System.out.println(userService.getTime());
        if ( !isCorrectDatabase() ){
            System.out.println("Exiting b/c wrong database");
            System.exit(0);
        }
        //Add shit
        if ( isCorrectDatabase() && userRepository.existsById("my-semi-secure-id-dude-Relight-My-Fire") ) {
            userRepository.deleteAll();
        }
        for (int i = 0; i < 5; i++) {
            User u = new User("UserDude " + i, "id-" + i);
            userRepository.save(u);
        }
    }
    @AfterAll
    public void cleanUpRepo(){
        User u = new User("UserDude", "my-semi-secure-id-dude-Relight-My-Fire");
        userRepository.save(u);
    }

    @Test
    void getAllUsers() {

        List<User> uList =  userService.getAllUsers();
        for (User u : uList){
            System.out.println(u);
        }
        //userRepository.findById("id-0").ifPresent();
        assertEquals(5, uList.size());
        assertTrue(userRepository.findById("id-0").isPresent());
        assertTrue(userRepository.findById("id-1").isPresent());
        assertTrue(userRepository.findById("id-2").isPresent());
        assertTrue(userRepository.findById("id-3").isPresent());
        assertTrue(userRepository.findById("id-4").isPresent());
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

    boolean isCorrectDatabase(){
        boolean isCorrect = true;
        String host_current = appProperties.getMongoHost();
        String db_current   = appProperties.getMongoDatabase();

        if (!db_current.equals(db_integration) || !host_current.equals(host_integration)){
            System.out.println("WRONG DATABASE OR HOST");
            System.out.println("DATABASE: \n" + this.db_integration + " - " + db_current);
            System.out.println("HOST:     \n" + this.host_integration + " - " + host_current);
            isCorrect = false;
        }
        return isCorrect;

    }
}