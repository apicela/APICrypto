package com.apicela.apicrypto.controllers;

import com.apicela.apicrypto.models.Coin;
import com.apicela.apicrypto.services.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Flux<Coin> getAllCoins() {
        return coinService.listAllCoins();
    }
}
