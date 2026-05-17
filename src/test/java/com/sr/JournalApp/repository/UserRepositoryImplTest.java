package com.sr.JournalApp.repository;

import com.sr.JournalApp.entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserRepositoryImplTest {

    @Autowired
    UserRepositoryImpl userRepository;

    @Test
    @Disabled
    public void getAllUsersForSaTest(){
        List<User> list = userRepository.getUsersForSA();
    }
}
