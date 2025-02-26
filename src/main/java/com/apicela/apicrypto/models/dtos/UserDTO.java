package com.apicela.apicrypto.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(@NotBlank(message = "Name cannot be empty") String name,
                            @NotBlank(message = "Last name cannot be empty") String lastName,
                            @Email(message = "E-mail inválido") String mail) { }
 