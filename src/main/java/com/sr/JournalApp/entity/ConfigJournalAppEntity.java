package com.sr.JournalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document
public class ConfigJournalAppEntity {
    private String key;
    private String value;
}
