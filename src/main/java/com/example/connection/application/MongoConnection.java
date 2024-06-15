package com.example.connection.application;

import com.mongodb.client.MongoClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class MongoConnection {
    private MongoClient mongoClient;
    private MongoTemplate mongoTemplate;
}
