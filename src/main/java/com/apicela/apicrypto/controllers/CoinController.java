package com.apicela.apicrypto.controllers;

import com.apicela.apicrypto.dtos.CoinResponse;
import com.apicela.apicrypto.services.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController()
@CrossOrigin("*")
@RequestMapping("/coins")
public class CoinController {
    @Autowired
    CoinService coinService;

    @GetMapping()
    @Cacheable(value = "cache10Min", key = "'listAllCoinsCache'", sync = true)
    @Scheduled(cron = "0 */15 8-23 * * *")
    public Mono<CoinResponse> getAllCoins() {
        return coinService.listAllCoins()
                .collectList()
                .map(coins -> new CoinResponse(coins, LocalDateTime.now()));
    }

//    @GetMapping()
//    @Cacheable(value = "cache10Min", key = "'listAllCoinsCache'", sync = true)
//    @Scheduled(cron = "0 */15 8-23 * * *")
//    public Mono<CoinResponse> getCoin() {
//        return coinService.listAllCoins()
//                .collectList()
//                .map(coins -> new CoinResponse(coins, LocalDateTime.now()));
//    }

}
