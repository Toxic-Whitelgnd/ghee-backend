package com.tarun.ghee.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class MongoConfig {

    private final long maxIdleTime = 60000;

    @Bean
    public MongoClient mongoClient() {
        // Create connection string for MongoDB

        ConnectionString connectionString = new ConnectionString(String.format("mongodb+srv://tarun:f1StMmlq25XUsg2e@cluster0.4d90q.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"));

        // Create MongoClientSettings with custom configurations
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .retryWrites(true) // Enable retry logic for failed writes
                .applyConnectionString(connectionString)
                .applyToConnectionPoolSettings((builder) -> {
                    builder.maxSize(100) // Maximum connections in the pool
                            .minSize(5)   // Minimum connections in the pool
                            .maxConnectionLifeTime(30, TimeUnit.MINUTES) // Maximum connection lifetime
                            .maxConnectionIdleTime(maxIdleTime, TimeUnit.MILLISECONDS); // Max idle time before the connection is closed
                })
                .applyToSocketSettings(builder -> {
                    builder.connectTimeout(2000, TimeUnit.MILLISECONDS) // Set connect timeout
                            .readTimeout(3000, TimeUnit.MILLISECONDS);  // Set socket read timeout
                })
                .applicationName("Ghee") // Application name for monitoring
                .build();

        // Return the MongoClient instance
        return MongoClients.create(clientSettings);
    }
    }