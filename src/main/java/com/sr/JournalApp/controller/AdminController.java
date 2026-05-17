package com.sr.JournalApp.controller;

import com.sr.JournalApp.entity.User;
import com.sr.JournalApp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    AdminController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/get-users")
    public ResponseEntity<?> getAllUsers(){

        List<User> users = userService.getAll();

        return ResponseEntity.ok(users);
    }

    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody User user){

        user.setRoles(new ArrayList<>());
        user.getRoles().add("USER");
        user.getRoles().add("ADMIN");

        userService.saveUser(user);

        return ResponseEntity.noContent().build();
    }
}
