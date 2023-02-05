package com.xml.xmlbackend.service;

import com.xml.xmlbackend.model.a1.Resenje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendResenje(String email, File file) throws MailException, IOException {
        SimpleMailMessage mail = new SimpleMailMessage();

        Path path = Paths.get(file.getPath());
        byte[] content = Files.readAllBytes(path);

        mail.setTo(email);
        mail.setSubject("Rešenje Zahteva");
        mail.setText("Poštovani,\n Šaljemo vam rešenje zahteva za autorska dela.\nSrdačan pozdrav.");

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Rešenje Zahteva");
            helper.setText("Poštovani šaljemo vam rešenje zahteva za autorska dela");
            helper.addAttachment("Rešenje.pdf", new ByteArrayResource(content));
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
