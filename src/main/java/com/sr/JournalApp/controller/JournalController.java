package com.sr.JournalApp.controller;

import com.sr.JournalApp.entity.JournalEntry;
import com.sr.JournalApp.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    JournalEntryService journalEntryService;

    @GetMapping("/{username}")
    public ResponseEntity<Collection<JournalEntry>> getAllEntriesByUser(@PathVariable String username){
        return ResponseEntity.ok(journalEntryService.getAll(username));
    }

//    @GetMapping("/{myId}")
//    public ResponseEntity<JournalEntry> getById(@PathVariable String myId){
//
//        Optional<JournalEntry> entry = journalEntryService.findById(myId);
//
//        if(entry.isEmpty()){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        return new ResponseEntity<>(entry.get(),HttpStatus.OK);
//    }

    @PostMapping("/{username}")
    public ResponseEntity<String> createEntry(@RequestBody JournalEntry journalEntry, @PathVariable String username){
        journalEntryService.saveEntry(journalEntry,username);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created Successfully");
    }

//    @PutMapping("/{myId}")
//    public ResponseEntity<JournalEntry> updateEntry(@RequestBody JournalEntry newEntry, @PathVariable String myId){
//        JournalEntry old = journalEntryService.findById(myId).orElse(null);
//
//        if(old!=null){
//            old.setContent(newEntry.getContent()!=null && !newEntry.getContent().isEmpty()? newEntry.getContent():"");
//            old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().isEmpty() ?newEntry.getTitle():"");
//        }
//
//        journalEntryService.saveEntry(old, username);
//        return ResponseEntity.ok(old);
//    }

    @DeleteMapping("/{username}/{myId}")
    public ResponseEntity<String> deleteEntry(@PathVariable String myId, @PathVariable String username) {
        journalEntryService.deleteById(myId,username);

        return ResponseEntity.ok("Journal Entry deleted Successfully.");


    }
}