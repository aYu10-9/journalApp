package com.edigest.jorunalApp.repository;

import com.edigest.jorunalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSA(){
        Query query = new Query();
        List<User> users = mongoTemplate.find(query, User.class, "users");
        System.out.println("--> TOTAL DOCUMENTS FOUND IN 'users' COLLECTION: " + users.size());
        return users;
    }
}