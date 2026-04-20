package com.sr.JournalApp.controller;

import com.sr.JournalApp.entity.JournalEntry;
import com.sr.JournalApp.entity.User;
import com.sr.JournalApp.service.JournalEntryService;
import com.sr.JournalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.util.Collection;
import java.util.Optional;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;


    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user){
            return ResponseEntity.ok(userService.updateUser(user));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(){
            userService.deleteByUsername();
            return ResponseEntity.noContent().build();
    }
}