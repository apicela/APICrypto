package com.apicela.apicrypto.controllers;

import com.apicela.apicrypto.models.Coin;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@CrossOrigin("*")
@RequestMapping("/coins")
public class CoinController {
    public List<Coin> getAllCoins(){

    }
}
