package com.apicela.apicrypto.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MonitoringDTO(@NotNull (message = "User ID cannot be empty") UUID userId,
                            @NotBlank (message = "Coin ID cannot be empty") String coinId,
                            @NotNull(message = "Price cannot be empty") Double price,
                            @NotNull (message = "Lower or higher cannot be empty") Boolean greatherThan) { }
