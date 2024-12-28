package com.deity.location.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

public class MongoConfig {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    // Base de datos 1: geodata
    @Bean("primaryMongoTemplate")
    public ReactiveMongoTemplate primaryMongoTemplate() {
        MongoClient client = MongoClients.create(mongoUri);
        return new ReactiveMongoTemplate(client, databaseName);
    }

    // Base de datos 2: citydata
    @Bean("secondaryMongoTemplate")
    public ReactiveMongoTemplate secondaryMongoTemplate() {
        MongoClient client = MongoClients.create(mongoUri);
        return new ReactiveMongoTemplate(client, databaseName);
    }

}
