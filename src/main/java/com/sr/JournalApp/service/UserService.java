package com.sr.JournalApp.service;

import com.sr.JournalApp.entity.JournalEntry;
import com.sr.JournalApp.entity.User;
import com.sr.JournalApp.exception.UserAlreadyExistsException;
import com.sr.JournalApp.exception.UserNotFoundException;
import com.sr.JournalApp.repository.JournalEntryRepository;
import com.sr.JournalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void createUser(User user){

        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        if(existingUser.isPresent()){
            throw new UserAlreadyExistsException("User already exists.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        saveUser(user);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User updateUser(User user){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();

        Optional<User> existingUser = userRepository.findByUsername(userName);
        Optional<User> newUsernameUser = userRepository.findByUsername(user.getUsername());

        if(!user.getUsername().equals(userName) && newUsernameUser.isPresent()) throw new UserAlreadyExistsException("User already exists.");

        User old = existingUser.get();

        old.setUsername(user.getUsername() != null && !user.getUsername().isEmpty() ? user.getUsername() : old.getUsername());
        old.setPassword(user.getPassword() != null && !user.getPassword().isEmpty() ? passwordEncoder.encode(user.getPassword()) : old.getPassword());

        userRepository.save(old);

        return old;
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(String id){
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username){ return userRepository.findByUsername(username);}

    public void deleteByUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        userRepository.deleteByUsername(userName);
    }

    public List<JournalEntry> getEntries(User user){
        return user.getJournalEntries();
    }

    public void deleteById(String id) throws Exception {
        Optional<User> entry = userRepository.findById(id);

        if (entry.isEmpty()) throw new Exception("Not Found");
        userRepository.deleteById(id);
    }
}
