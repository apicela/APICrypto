package com.apicela.apicrypto.controllers;

import com.apicela.apicrypto.models.dtos.UserDTO;
import com.apicela.apicrypto.models.requests.RegisterUserDTO;
import com.apicela.apicrypto.models.responses.DefaultApiResponse;
import com.apicela.apicrypto.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@Log4j2
@Tag(name = "User Controller")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
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

    @GetMapping("/{id}")
    @Operation(summary = "Find object by Id", description = "Here, you can get a specific object filtering by your ID")
    public Mono<ResponseEntity<Object>> getUserById(@PathVariable(value = "id") UUID id) {
        return userService.findById(id).map(user -> {
            DefaultApiResponse<UserDTO> response = new DefaultApiResponse<>(
                    "ok",
                    user,
                    HttpStatus.OK.value()
            );
            log.info("{}", response);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        });
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Find object by Id", description = "Here, you can get a specific object filtering by your ID")
    public Mono<ResponseEntity<Object>> deleteMonitoring(@PathVariable(value = "id") UUID id) {
        return userService.deleteById(id).then(Mono.fromCallable(() -> {
            DefaultApiResponse<Void> response = new DefaultApiResponse<>(
                    "ok",
                    HttpStatus.OK.value()
            );
            log.info("User with ID {} deleted successfully", id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }));

    }
}
