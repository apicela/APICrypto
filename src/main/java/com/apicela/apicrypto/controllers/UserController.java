package com.apicela.apicrypto.controllers;

import com.apicela.apicrypto.models.dtos.UserDTO;
import com.apicela.apicrypto.models.responses.ApiResponse;
import com.apicela.apicrypto.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@Tag(name = "User Controller")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "CREATE", description = "Here, you can create a new object for your entity")
    public Mono<ResponseEntity<Object>> saveUser(@RequestBody @Valid UserDTO userDTO) {
        return userService.save(userDTO)
                .map(savedUser -> {
                    ApiResponse<UserDTO> response = new ApiResponse<>(
                            "Usuário criado com sucesso",
                            savedUser,
                            HttpStatus.CREATED.value()
                    );
                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                });
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find object by Id", description = "Here, you can get a specific object filtering by your ID")
    public Mono<ResponseEntity<Object>> getUserById(@PathVariable(value = "id") UUID id) {
        return userService.findById(id).map(user -> {
            ApiResponse<UserDTO> response = new ApiResponse<>(
                    "ok",
                    user,
                    HttpStatus.OK.value()
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        });
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Find object by Id", description = "Here, you can get a specific object filtering by your ID")
    public Mono<ResponseEntity<Object>> deleteMonitoring(@PathVariable(value = "id") UUID id) {
        return userService.deleteById(id).then(Mono.fromCallable(() -> {
            ApiResponse<Void> response = new ApiResponse<>(
                    "ok",
                    HttpStatus.OK.value()
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }));

    }
}
