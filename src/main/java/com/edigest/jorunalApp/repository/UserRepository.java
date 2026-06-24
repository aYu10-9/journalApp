package com.edigest.jorunalApp.repository;

import com.edigest.jorunalApp.entity.JournalEntry;
import com.edigest.jorunalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String username);

    User deleteByUserName(String username);
}
