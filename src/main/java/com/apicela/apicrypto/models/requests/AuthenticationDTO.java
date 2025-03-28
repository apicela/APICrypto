package com.apicela.apicrypto.models.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(@Email(message = "E-mail invalid") String email,
                              @NotBlank(message = "Password cannot be empty") String password ) {

}