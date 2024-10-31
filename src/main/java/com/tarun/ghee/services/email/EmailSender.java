package com.tarun.ghee.services.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class EmailSender {

    @Autowired
    private JavaMailSender jm;

    @Async
    public void SendEmail(SimpleMailMessage sm) {

        try {
            jm.send(sm);
            log.info("Email Delivered");
        } catch (Exception e) {
            log.error("Failed to send the Email" + e.getMessage());
        }
//        zydv pedw houl ulbi
    }

    @Async
    public CompletableFuture<Void> SendEmailBussiness(SimpleMailMessage sm) {

        try {
            jm.send(sm);
            log.info("Email Delivered");
            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            log.error("Failed to send the Email" + e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
//        zydv pedw houl ulbi
    }

    @Async
    public void SendEmailMessage(MimeMessagePreparator sm) {

        try {

            jm.send((MimeMessagePreparator) sm);
            log.info("Email Delivered for Resetin Password");
        } catch (Exception e) {
            log.error("Failed to send the Email for reseting the Password" + e.getMessage());
        }
    }
}
