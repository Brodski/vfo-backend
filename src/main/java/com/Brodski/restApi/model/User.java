package com.Brodski.restApi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document //tells mongodb that this object should be treated as document to be stored in collection
public class User {

    @Id
    String id;
    public String username;

    public User(){}

    public User(String username){
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String toString(){
        return String.format("User: username: %s, id: %s", this.username, this.id);
    }

}
