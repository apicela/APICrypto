package com.apicela.apicrypto.services;

import com.apicela.apicrypto.models.dtos.Mail;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(Mail mail) {
        var message = new SimpleMailMessage();
        message.setFrom("trab.jamilsouza@gmail.com");
        message.setTo(mail.to());
        message.setSubject(mail.title());
        message.setText(mail.message());
        javaMailSender.send(message);
    }
}
