package com.apicela.apicrypto.controllers;

import com.apicela.apicrypto.models.dtos.Coin;
import com.apicela.apicrypto.models.dtos.UserDTO;
import com.apicela.apicrypto.models.responses.CoinListResponseDTO;
import com.apicela.apicrypto.models.responses.DefaultApiResponse;
import com.apicela.apicrypto.services.CoinService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController()
@CrossOrigin("*")
@Log4j2
@RequestMapping("/coins")
@Tag(name = "Coin Controller")
public class CoinController {
    @Autowired
    CoinService coinService;

    @GetMapping("/all")
    public  Mono<ResponseEntity<Object>> getAllCoins() {
        return coinService.listAllCoins()
                .collectList()
                .map(coins -> {
                    DefaultApiResponse<CoinListResponseDTO> response = new DefaultApiResponse<>(
                        "ok",
                        new CoinListResponseDTO(coins, LocalDateTime.now()),
                        HttpStatus.OK.value());
                    log.info("{}", response);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                });
    }

    @GetMapping()
    public  Mono<ResponseEntity<Object>> getCoinById(@RequestParam(value = "name") String name) {
        return coinService.findById(name)
                .map(coin -> {
                    DefaultApiResponse<Coin> response = new DefaultApiResponse<>(
                            "ok",
                            coin,
                            HttpStatus.OK.value()
                    );
                    log.info("{}", response);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                });
    }

}
