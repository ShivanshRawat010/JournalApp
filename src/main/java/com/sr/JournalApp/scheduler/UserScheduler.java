package com.sr.JournalApp.scheduler;

import com.sr.JournalApp.cache.AppCache;
import com.sr.JournalApp.entity.User;
import com.sr.JournalApp.enums.Sentiment;
import com.sr.JournalApp.model.SentimentData;
import com.sr.JournalApp.repository.UserRepositoryImpl;
import com.sr.JournalApp.service.MailService;
import com.sr.JournalApp.service.SentimentAnalysisService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserScheduler {

    private final UserRepositoryImpl userRepository;
    private final SentimentAnalysisService sentimentAnalysisService;
    private final AppCache appCache;
    private final KafkaTemplate<String, SentimentData> kafkaTemplate;

    UserScheduler (
            MailService mailService,
            UserRepositoryImpl userRepository,
            SentimentAnalysisService sentimentAnalysisService,
            AppCache appCache,
            KafkaTemplate<String, SentimentData> kafkaTemplate
    ){
        this.userRepository = userRepository;
        this.sentimentAnalysisService = sentimentAnalysisService;
        this.appCache = appCache;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(cron = "0 0 9 ? * SUN")
    public void sendSaMail(){
        List<User> list = userRepository.getUsersForSA();

        for(User user : list){
            Sentiment sentiment = sentimentAnalysisService.getSentimentAnalysis(user);
            if(sentiment!=null){
                SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment(sentiment.toString()).build();
                kafkaTemplate.send("weekly-sentiments", user.getEmail(), sentimentData);
            }

        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void resetAppCache(){
        appCache.init();
    }

}
