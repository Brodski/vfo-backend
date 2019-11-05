package com.Brodski.restApi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class AppProperties {


    @Value("${logging.level.root}")
    private String serverPort;

    @Value("${spring.data.mongodb.database}")
    private String mongoDatabase;

    @Value("${spring.data.mongodb.host}")
    private String mongoHost;


    public String getMongoDatabase(){
        return this.mongoDatabase;
    }
    public String getMongoHost(){
        return this.mongoHost;
    }
}
