package com.sr.JournalApp.service;

import com.sr.JournalApp.entity.JournalEntry;
import com.sr.JournalApp.entity.User;
import com.sr.JournalApp.exception.EntryNotFoundException;
import com.sr.JournalApp.exception.UserNotFoundException;
import com.sr.JournalApp.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public void saveEntry(JournalEntry journalEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        JournalEntry saved = journalEntryRepository.save(journalEntry);

        User user = userService.findByUsername(username).get();
        user.getJournalEntries().add(saved);
        userService.saveUser(user);
    }

    public List<JournalEntry> getAll(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userService.findByUsername(username);

        if(user.isEmpty()) throw new UserNotFoundException("User not found.");

        return user.get().getJournalEntries();
    }

    public JournalEntry findById(String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<JournalEntry> list = userService.findByUsername(username).get().getJournalEntries();

        List<JournalEntry> entry = list.stream().filter(x -> x.getId().equals(id)).toList();

        if(entry.isEmpty()){
            throw new EntryNotFoundException("Invalid journal entry id");
        }

        return entry.get(0);
    }

    @Transactional
    public void deleteById(String id) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userService.findByUsername(username).get();

        List<JournalEntry> list = user.getJournalEntries();

        boolean exists = list.stream()
                .anyMatch(entry -> entry.getId().equals(id));

        if (!exists) {
            throw new EntryNotFoundException("Journal entry not found or not authorized");
        }

        list.removeIf(entry -> entry.getId().equals(id));
        userService.saveUser(user);
        journalEntryRepository.deleteById(id);
    }
}
