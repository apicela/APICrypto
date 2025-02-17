package com.apicela.apicrypto.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record CoinListResponseDTO(List<Coin> coins,
                                  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
                           LocalDateTime lastUpdated) {
}
