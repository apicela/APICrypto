package com.apicela.apicrypto.controllers;

import com.apicela.apicrypto.dtos.Mail;
import com.apicela.apicrypto.services.MailService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {
    private final MailService mailService;

    public MailController(MailService mailService){
        this.mailService = mailService;
    }

    @PostMapping
    public ResponseEntity<Object> sendMail(@RequestBody Mail mail){
        mailService.sendMail(mail);
        return ResponseEntity.status(HttpStatus.OK).body("E-mail enviado");
    }


}
