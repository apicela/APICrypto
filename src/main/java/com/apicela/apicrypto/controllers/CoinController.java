package com.apicela.apicrypto.controllers;

import com.apicela.apicrypto.models.dtos.Coin;
import com.apicela.apicrypto.models.dtos.CoinListResponseDTO;
import com.apicela.apicrypto.services.CoinService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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
    @Cacheable(value = "cache10Min", key = "'listAllCoinsCache'", sync = true)
    @Scheduled(cron = "0 */15 8-23 * * *")
    public Mono<CoinListResponseDTO> getAllCoins() {
        return coinService.listAllCoins()
                .collectList()
                .map(coins -> new CoinListResponseDTO(coins, LocalDateTime.now()));
    }

    @GetMapping("/{id}")
    @Cacheable(value = "cache1Min", key = "'CoinCache'", sync = true)
    public Mono<ResponseEntity<Coin>> getCoinById(@PathVariable(value = "id") String name) {
        return coinService.findById(name)
                .map(coin -> ResponseEntity.status(HttpStatus.OK).body(coin))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
    }


}
