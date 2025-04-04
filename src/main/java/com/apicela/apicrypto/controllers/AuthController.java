package com.apicela.apicrypto.controllers;

import com.apicela.apicrypto.models.dtos.UserDTO;
import com.apicela.apicrypto.models.requests.AuthenticationDTO;
import com.apicela.apicrypto.models.requests.RegisterUserDTO;
import com.apicela.apicrypto.models.responses.DefaultApiResponse;
import com.apicela.apicrypto.services.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping("/auth")
@Log4j2
public class AuthController {
    final ReactiveUserDetailsService userDetailsService;
    final TokenService tokenService;
    final PasswordEncoder passwordEncoder;

    public AuthController(ReactiveUserDetailsService userDetailsService, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login (@RequestBody AuthenticationDTO authenticationDTO) {
        return userDetailsService.findByUsername(authenticationDTO.email())
                .flatMap(user -> {
                    if (passwordEncoder.matches(authenticationDTO.password(), user.getPassword())) {
                        String token = tokenService.generate(authenticationDTO.email());
                        return Mono.just(ResponseEntity.ok(token));
                    } else {
                        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"));
                    }
                })
                .onErrorResume(UsernameNotFoundException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found"))
                );
    }


}
