package com.sr.JournalApp.Services;

import com.sr.JournalApp.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SendMailTest {

    @Autowired
    private MailService mailService;

    @Test
    public void sendMailTest(){
        mailService.sendEmail("somyarawat5674@gmail.com","Test Email", "Is ts still working??");
    }
}
