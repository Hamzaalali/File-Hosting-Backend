package com.example.demo;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoIterable;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.DynamicPropertySource;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor
public class MongoDBTest {
    public MongoClient mongoClient = MongoClients.create("mongodb://root:root@localhost:27015");

    @DynamicPropertySource
    static void mongoProperties(org.springframework.test.context.DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.host", ()->"localhost");
        registry.add("spring.data.mongodb.port", ()->27015);
        registry.add("spring.data.mongodb.database", () -> "admin");
        registry.add("spring.data.mongodb.username", () -> "root");
        registry.add("spring.data.mongodb.password", () -> "root");
    }
    @BeforeEach
    public  void beforeEach(){
        mongoClient.getDatabase("admin");
    }
    @AfterEach
    public  void afterEach(){
        MongoDatabase database=mongoClient.getDatabase("admin");
        MongoIterable<String> collectionsNames=database.listCollectionNames();
        for(String s:collectionsNames){
            if(Objects.equals(s, "material")||Objects.equals(s, "material_grade") ||Objects.equals(s, "ut_test") )
                database.getCollection(s).drop();
        }

    }
}
