package com.tarun.ghee.services.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailSender {

    @Autowired
    private JavaMailSender jm;

    @Async
    public boolean  SendEmail(SimpleMailMessage sm){

        try{
            jm.send(sm);
            log.info("Email Delivered");
        } catch (Exception e) {
            log.error("Failed to send the Email"+e.getMessage());
            return false;
        }
//        zydv pedw houl ulbi
        return true;
    }
}
