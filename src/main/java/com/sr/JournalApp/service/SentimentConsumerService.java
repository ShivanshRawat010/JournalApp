package com.sr.JournalApp.service;

import com.sr.JournalApp.enums.Sentiment;
import com.sr.JournalApp.model.SentimentData;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SentimentConsumerService {

    private final MailService mailService;

    SentimentConsumerService(MailService mailService){
        this.mailService = mailService;
    }

    @KafkaListener(topics = "weekly-sentiments", groupId = "weekly-sentiment-group")
    public void consume(SentimentData sentimentData){
        sendMail(sentimentData);
    }

    private void sendMail(SentimentData sentimentData){
        mailService.sendEmail(sentimentData.getEmail(), "This weeks sentiment analysis is : ", sentimentData.getSentiment());
    }
}
