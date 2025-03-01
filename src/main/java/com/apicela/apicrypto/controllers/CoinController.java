package com.apicela.apicrypto.controllers;

import com.apicela.apicrypto.models.dtos.Coin;
import com.apicela.apicrypto.models.dtos.CoinListResponseDTO;
import com.apicela.apicrypto.services.CoinService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController()
@CrossOrigin("*")
@RequestMapping("/coins")
@Tag(name = "Coin Controller")
public class CoinController {
    @Autowired
    CoinService coinService;

    @GetMapping()
    public Mono<CoinListResponseDTO> getAllCoins() {
        return coinService.listAllCoins()
                .collectList()
                .map(coins -> new CoinListResponseDTO(coins, LocalDateTime.now()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Coin>> getCoinById(@PathVariable(value = "id") String name) {
        return coinService.findById(name)
                .map(coin -> ResponseEntity.status(HttpStatus.OK).body(coin));
    }

}
