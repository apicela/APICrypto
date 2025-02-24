package com.apicela.apicrypto.models.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record MonitoringDTO(@NotBlank (message = "User ID cannot be empty") UUID userId,
                            @NotBlank (message = "Coin ID cannot be empty") String coinId,
                            @NotBlank (message = "Price cannot be empty") double price,
                            @NotBlank (message = "Lower or higher cannot be empty") boolean greatherThan) { }
