package com.apicela.apicrypto.models.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterUserDTO(@NotBlank(message = "Name cannot be empty") String name,
                              @NotBlank(message = "Last name cannot be empty") String lastName,
                              @Email(message = "E-mail invalid") String mail,
                              @NotBlank(message = "Password cannot be empty") String password ) {

}