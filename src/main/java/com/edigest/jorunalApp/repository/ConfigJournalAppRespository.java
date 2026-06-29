package com.edigest.jorunalApp.repository;

import com.edigest.jorunalApp.entity.ConfigJournalAppEntity;
import com.edigest.jorunalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRespository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}
