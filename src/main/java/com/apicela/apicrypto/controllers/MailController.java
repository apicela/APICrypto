package com.apicela.apicrypto.controllers;

import com.apicela.apicrypto.models.dtos.Mail;
import com.apicela.apicrypto.services.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/mail")
@Tag(name = "Mail Controller")
public class MailController {
    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping
    @Operation(summary = "Send a mail")
    public Mono<ResponseEntity<Object>> sendMail (@RequestBody Mail mail) {
        mailService.sendMail(mail);
        return Mono.just(ResponseEntity.status(HttpStatus.OK).body("E-mail enviado"));
    }
}
