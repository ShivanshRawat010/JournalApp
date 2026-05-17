package com.sr.JournalApp.controller;

import com.sr.JournalApp.entity.JournalEntry;
import com.sr.JournalApp.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<Collection<JournalEntry>> getAllEntriesByUser(){
        return ResponseEntity.ok(journalEntryService.getAll());
    }

    @GetMapping("/{myId}")
    public ResponseEntity<JournalEntry> getById(@PathVariable String myId){

        JournalEntry entry = journalEntryService.findById(myId);

        return new ResponseEntity<>(entry,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createEntry(@RequestBody JournalEntry journalEntry){
        journalEntryService.saveEntry(journalEntry);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created Successfully");
    }

    @PutMapping("/{myId}")
    public ResponseEntity<JournalEntry> updateEntry(@RequestBody JournalEntry newEntry, @PathVariable String myId){
        JournalEntry old = journalEntryService.findById(myId);

        if(old!=null){
            old.setContent(newEntry.getContent()!=null && !newEntry.getContent().isEmpty()? newEntry.getContent():"");
            old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().isEmpty() ?newEntry.getTitle():"");
        }

        old.setDate(LocalDateTime.now());

        journalEntryService.updateEntry(old);
        return ResponseEntity.ok(old);
    }

    @DeleteMapping("/{myId}")
    public ResponseEntity<String> deleteEntry(@PathVariable String myId) {
        journalEntryService.deleteById(myId);

        return ResponseEntity.ok("Journal Entry deleted Successfully.");


    }
}