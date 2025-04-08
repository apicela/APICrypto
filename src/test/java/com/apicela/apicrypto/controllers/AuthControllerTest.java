package com.apicela.apicrypto.controllers;

import com.apicela.apicrypto.models.requests.AuthenticationDTO;
import com.apicela.apicrypto.models.requests.RegisterUserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testRegisterAndLoginSuccess() {
        // Criação do usuário
        RegisterUserDTO registerUserDTO = new RegisterUserDTO(
                "Jamil",
                "Apicela",
                "jamil@mail.com",
                "123456"
        );

        // Chamada POST /auth/register
        webTestClient.post()
                .uri("/auth/register")
                .bodyValue(registerUserDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.message").isEqualTo("User created successfully");

        // Login com o usuário criado
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(
                "jamil@mail.com",
                "123456"
        );

        // Chamada POST /auth/login
        webTestClient.post()
                .uri("/auth/login")
                .bodyValue(authenticationDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.message").isEqualTo("ok")
                .jsonPath("$.data.token").exists();
    }

    @Test
    void testLoginWithInvalidPassword() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(
                "jamil@mail.com",
                "senhaErrada"
        );

        webTestClient.post()
                .uri("/auth/login")
                .bodyValue(authenticationDTO)
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody()
                .jsonPath("$.message").isEqualTo("User not found");
    }

    @Test
    void testLoginWithUserNotFound() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(
                "emailInexistente@mail.com",
                "123456"
        );

        webTestClient.post()
                .uri("/auth/login")
                .bodyValue(authenticationDTO)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Invalid email or password");
    }
}