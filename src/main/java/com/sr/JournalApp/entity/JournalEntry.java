package com.sr.JournalApp.entity;

import com.sr.JournalApp.enums.Sentiment;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document
@Data
public class JournalEntry {

    @Id
    private String id;
    private String title;
    private String content;
    private LocalDateTime date;
    private Sentiment sentiment;
}
