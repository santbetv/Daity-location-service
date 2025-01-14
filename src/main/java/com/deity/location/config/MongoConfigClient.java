package com.deity.location.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//Configuración de la conexión a la base de datos de Mongo Atlas
@Configuration
public class MongoConfigClient {

    @Value("${url_conexion_mongo_atlas}")
    private String databaseNameAtlas;

    @Bean
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(databaseNameAtlas);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(settings);
    }
}
