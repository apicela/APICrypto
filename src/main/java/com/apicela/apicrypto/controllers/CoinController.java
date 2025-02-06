package com.apicela.apicrypto.controllers;

import com.apicela.apicrypto.models.Coin;
import com.apicela.apicrypto.services.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController()
@CrossOrigin("*")
@RequestMapping("/coins")
public class CoinController {
    @Autowired
    CoinService coinService;

    @GetMapping()
    @Cacheable(value = "cache10Min", key = "'listAllCoinsCache'", sync = true)
    public Flux<Coin> getAllCoins() {
        return coinService.listAllCoins();
    }

    @GetMapping("/endpoint1")
    @Cacheable(value = "cache10Min", key = "'testeHello'")
    public String Hello() {
        return "World";
    }

    @GetMapping("/endpoint2")
    @Cacheable(value = "cache1Dia", key = "'testeHello2'")
    public String World() {
        return "World";
    }
}
