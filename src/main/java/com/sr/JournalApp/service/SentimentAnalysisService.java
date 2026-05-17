package com.sr.JournalApp.service;

import com.sr.JournalApp.entity.JournalEntry;
import com.sr.JournalApp.entity.User;
import com.sr.JournalApp.enums.Sentiment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SentimentAnalysisService {

    private final UserService userService;

    SentimentAnalysisService(UserService userService){
        this.userService = userService;
    }

    public Sentiment getSentimentAnalysis(User user){

        List<JournalEntry> list = userService.getEntries(user);

        List<Sentiment> sentiments = list.stream()
                .filter(entry ->
                    entry.getDate().isAfter(LocalDateTime.now().minusDays(7))
                ).map(JournalEntry::getSentiment)
        .toList();

        Map<Sentiment,Integer> map = new HashMap<>();

        for(Sentiment sentiment : sentiments){
            if(sentiment!=null){
                map.put(sentiment,map.getOrDefault(sentiment,0)+1);
            }
        }

        int max = 0;
        Sentiment res = null;

        for(Sentiment key : map.keySet()){
            if(map.get(key)>max){
                res = key;
                max = map.get(key);
            }
        }

        return res;
    }
}
