package com.Brodski.restApi.UserRepository;

import com.Brodski.restApi.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> { //Id is a string
    public User findByUsername(String username);
    //public List<User> findById(String Id);
    public User findByGoogleId(String id);
    public void deleteUserByGoogleId(String id);
}


