package com.Brodski.restApi.model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter @Setter
@ToString
@Document //tells mongodb that this object should be treated as document to be stored in collection
public class User {

    public String username;
    @Id //If googleId is saved in db before given a value assigned then spring auto creates an Id. @Id is necessary for spring to properly work with database.
    public String googleId;
    public String pictureUrl;
    public CustomShelf[] customShelfs;

    public User(){}

    public User(String username){
        this.username = username;
    }
    public User(String username, String googleId){
        this.username = username;
        this.googleId = googleId;
    }

    public User(String username, CustomShelf[] customShelfs, String pictureUrl){
        this.customShelfs = customShelfs;
        this.pictureUrl = pictureUrl;
        this.username = username;

    }
}
