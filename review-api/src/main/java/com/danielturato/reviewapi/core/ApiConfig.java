package com.danielturato.reviewapi.core;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApiConfig {

    private final Environment env;

    public ApiConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public MongoClient mongoClient() {
        String mongoUri = env.getProperty("SPRING_DATA_MONGODB_URI");
        return MongoClients.create(mongoUri);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
