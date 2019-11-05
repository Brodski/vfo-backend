/*
    Not actually integration testing
 */

package com.Brodski.restApi.Integration;

import com.Brodski.restApi.Service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


//For integration tests, use @SpringBootTest https://www.baeldung.com/spring-boot-testing
@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {


    @Autowired
    private UserService userService;

    final private String db_integration = "IntegrationRestApi";
    final private String host_integration ="localhost";

    @BeforeAll
    public void init(){
        if ( !isCorrectDatabase() ){
            System.out.println("Exiting b/c wrong database");
            System.exit(0);
        }


    }

    @Test
    void getAllUsers() {
        System.out.println(userService.getAllUsers());
        System.out.println(userService.getTime());
        System.out.println(userService.getMongoHost());
        System.out.println(userService.getMongoDatabase());
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
        String host_current = userService.getMongoHost();
        String db_current = userService.getMongoDatabase();
        System.out.println(host_current);
        System.out.println(db_current);

        if (!db_current.equals(db_integration) || !host_current.equals(host_integration)){
            System.out.println("WRONG DATABASE OR HOST");
            System.out.println("DATABASE: \n" + this.db_integration + " - " + db_current);
            System.out.println("HOST:     \n" + this.host_integration + " - " + host_current);
            isCorrect = false;
        }
        return isCorrect;

    }
}