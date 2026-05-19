package com.sr.JournalApp.Services;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    @Disabled
    public void redisTest(){
        redisTemplate.opsForValue().set("username","shivansh");
        Object str =  redisTemplate.opsForValue().get("newKey");
        int a = 1;
    }
}
