package com.sr.JournalApp.service;

import com.sr.JournalApp.entity.JournalEntry;
import com.sr.JournalApp.entity.User;
import com.sr.JournalApp.exception.UserNotFoundException;
import com.sr.JournalApp.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    final JournalEntryRepository journalEntryRepository;
    final UserService userService;

    public JournalEntryService(JournalEntryRepository journalEntryRepository, UserService userService){
        this.journalEntryRepository = journalEntryRepository;
        this.userService = userService;
    }

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username){

        Optional<User> userOptional = userService.findByUsername(username);

        if(userOptional.isEmpty()) throw new UserNotFoundException("User not found");

        JournalEntry saved = journalEntryRepository.save(journalEntry);

        User user = userOptional.get();
        user.getJournalEntries().add(saved);
        userService.saveUser(user);

    }

    public List<JournalEntry> getAll(String username){

        Optional<User> user = userService.findByUsername(username);

        if(user.isEmpty()) throw new UserNotFoundException("User not found.");

        return user.get().getJournalEntries();
    }

    public Optional<JournalEntry> findById(String id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public void deleteById(String id, String username){

        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));


        List<JournalEntry> list = user.getJournalEntries();

        list.removeIf(entry -> entry.getId().equals(id));

        userService.saveUser(user);

        journalEntryRepository.deleteById(id);
    }
}
