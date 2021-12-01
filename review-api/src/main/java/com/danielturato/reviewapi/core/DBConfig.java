package com.danielturato.reviewapi.core;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class DBConfig {

    private final Environment env;

    public DBConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public MongoClient mongoClient() {
        String mongoUri = env.getProperty("SPRING_DATA_MONGODB_URI");
        return MongoClients.create(mongoUri);
    }
}
