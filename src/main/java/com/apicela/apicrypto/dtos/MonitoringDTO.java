package com.apicela.apicrypto.dtos;

import jakarta.validation.constraints.NotBlank;

public record MonitoringDTO(@NotBlank (message = "User ID cannot be empty") long userId,
                            @NotBlank (message = "Coin ID cannot be empty") String coinId,
                            @NotBlank (message = "Percentage cannot be empty") float percentageDifference) { }
