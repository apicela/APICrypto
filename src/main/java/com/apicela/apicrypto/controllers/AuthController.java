package com.apicela.apicrypto.controllers;

import com.apicela.apicrypto.models.dtos.UserDTO;
import com.apicela.apicrypto.models.requests.AuthenticationDTO;
import com.apicela.apicrypto.models.requests.RegisterUserDTO;
import com.apicela.apicrypto.models.responses.DefaultApiResponse;
import com.apicela.apicrypto.models.responses.TokenResponse;
import com.apicela.apicrypto.services.TokenService;
import com.apicela.apicrypto.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping("/auth")
@Log4j2
public class AuthController {
    final UserService userService;
    final TokenService tokenService;
    final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<DefaultApiResponse<TokenResponse>>> login(@RequestBody AuthenticationDTO authenticationDTO) {
        return userService.findByUsername(authenticationDTO.email())
                .flatMap(user -> {
                    if (passwordEncoder.matches(authenticationDTO.password(), user.getPassword())) {
                        String token = tokenService.generate(authenticationDTO.email());

                        DefaultApiResponse<TokenResponse> response = new DefaultApiResponse<>(
                                "ok",
                                new TokenResponse(token),
                                HttpStatus.OK.value());

                        return Mono.just(ResponseEntity.ok(response));
                    } else {
                        DefaultApiResponse<TokenResponse> response = new DefaultApiResponse<>(
                                "User not found",
                                null,
                                HttpStatus.UNAUTHORIZED.value());

                        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response));
                    }
                })
                .switchIfEmpty(Mono.defer(() -> {
                    DefaultApiResponse<TokenResponse> response = new DefaultApiResponse<>(
                            "Invalid email or password",
                            null,
                            HttpStatus.NOT_FOUND.value());

                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(response));
                }));

    }

    @PostMapping("/register")
    @Operation(summary = "CREATE", description = "Here, you can create a new object for your entity")
    public Mono<ResponseEntity<Object>> saveUser(@RequestBody @Valid RegisterUserDTO userDTO) {
        return userService.save(userDTO)
                .map(savedUser -> {
                    DefaultApiResponse<UserDTO> response = new DefaultApiResponse<>(
                            "User created successfully",
                            savedUser,
                            HttpStatus.CREATED.value()
                    );
                    log.info("{}", response);
                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                });
    }

}
