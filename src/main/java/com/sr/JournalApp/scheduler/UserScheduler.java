package com.sr.JournalApp.scheduler;

import com.sr.JournalApp.cache.AppCache;
import com.sr.JournalApp.entity.User;
import com.sr.JournalApp.enums.Sentiment;
import com.sr.JournalApp.repository.UserRepositoryImpl;
import com.sr.JournalApp.service.MailService;
import com.sr.JournalApp.service.SentimentAnalysisService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserScheduler {
    //bring the users and send the mail

    private final MailService mailService;
    private final UserRepositoryImpl userRepository;
    private final SentimentAnalysisService sentimentAnalysisService;
    private final AppCache appCache;

    UserScheduler (
            MailService mailService,
            UserRepositoryImpl userRepository,
            SentimentAnalysisService sentimentAnalysisService,
            AppCache appCache
    ){
        this.mailService = mailService;
        this.userRepository = userRepository;
        this.sentimentAnalysisService = sentimentAnalysisService;
        this.appCache = appCache;
    }

    @Scheduled(cron = "0 0 9 ? * SUN")
    public void sendSaMail(){
        List<User> list = userRepository.getUsersForSA();

        for(User user : list){
            Sentiment sentiment = sentimentAnalysisService.getSentimentAnalysis(user);
            if(sentiment!=null){
                mailService
                        .sendEmail(user.getEmail(), "Sentiment analysis this week.", sentiment.toString());
            }

        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void resetAppCache(){
        appCache.init();
    }

}
