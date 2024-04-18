package org.example.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class db {
    private static MongoClient mongoClient;
    private static String connectionString = "Fuck off!! käy hakee oma  ";


    private db() {
    }

    public static synchronized MongoClient getMongoClient() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(connectionString);
        }
        return mongoClient;
    }
}