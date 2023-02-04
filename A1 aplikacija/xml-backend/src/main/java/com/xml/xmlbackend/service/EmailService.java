package com.xml.xmlbackend.service;

import com.xml.xmlbackend.model.a1.Resenje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendResenje(String email, Resenje resenje) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(email);
        mail.setSubject("Rešenje Zahteva");

        mail.setText("Rešenje zahteva:");
        try{
            mailSender.send(mail);
        }catch (RuntimeException e){
            throw new IllegalStateException("failed to send email");
        }
    }

}
