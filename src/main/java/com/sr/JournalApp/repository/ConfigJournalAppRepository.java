package com.sr.JournalApp.repository;

import com.sr.JournalApp.entity.ConfigJournalAppEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, String> {

}
