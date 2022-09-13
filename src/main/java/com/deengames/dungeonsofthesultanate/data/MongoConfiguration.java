package com.deengames.dungeonsofthesultanate.data;

import com.mongodb.client.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;

@Configuration
public class MongoConfiguration {
    private static final String MONGO_DB_URL = "localhost";
    private static final String MONGO_DB_NAME = "embeded_db";

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        var mongo = new MongoClientFactoryBean();
        mongo.setHost(MONGO_DB_URL);
        MongoClient mongoClient = mongo.getObject();
        var mongoTemplate = new MongoTemplate(mongoClient, MONGO_DB_NAME);
        return mongoTemplate;
    }
}
