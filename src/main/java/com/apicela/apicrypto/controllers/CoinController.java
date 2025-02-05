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
    @Cacheable(value = "cache10Min", key = "'listAllCoinsCache'")
    public Flux<Coin> getAllCoins() {
        return coinService.listAllCoins();
    }

    @GetMapping("/hello")
    @Cacheable(value = "cache1Dia", key = "'testeHello'")
    public String Hello() {
        return "Hello World";
    }
}
