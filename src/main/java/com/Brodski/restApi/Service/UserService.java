package com.Brodski.restApi.Service;

import com.Brodski.restApi.UserRepository.UserRepository;
import com.Brodski.restApi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;
   // private final UserRepository userRepo;

  //  public UserService(UserRepository userRepository){
 //       this.userRepo = userRepository;
//    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public User createUser(String username ){
        return userRepo.save(new User(username));
    }

    public void testCreate(String username ){
        this.createUser(username);
        return;
    }

    public String getTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd, HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String s = dtf.format(now);
        return s;
    }
}
