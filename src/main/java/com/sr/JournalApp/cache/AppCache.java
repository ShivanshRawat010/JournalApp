package com.sr.JournalApp.cache;

import com.sr.JournalApp.entity.ConfigJournalAppEntity;
import com.sr.JournalApp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AppCache {

    private final ConfigJournalAppRepository configJournalAppRepository;

    public AppCache(ConfigJournalAppRepository configJournalAppRepository){
        this.configJournalAppRepository = configJournalAppRepository;
    }

    public Map<String,String> map = new HashMap<>();

    @PostConstruct
    public void init(){
        for(ConfigJournalAppEntity configJournalAppEntity : configJournalAppRepository.findAll()){
            map.put(configJournalAppEntity.getKey(),configJournalAppEntity.getValue());
        }
    }
}
