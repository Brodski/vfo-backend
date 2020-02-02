package com.Brodski.restApi;
//Constant interface
//https://en.wikipedia.org/wiki/Constant_interface
//https://stackoverflow.com/questions/2659593/what-is-the-use-of-interface-constants

public final class SecretKeyz { // this file has been updated  changed to ActualSecreteKeys (and .gitignore)

    private SecretKeyz() {
        //restrict instantiation
    }
    public static final String APIKEY = "api key ---";
    public static final String CLIENT_ID = "351423957584-47knbsklt23b9cbsfgp2naqeklrd9o5u.apps.googleusercontent.com";


}

// client Id's must match on frontend & backend