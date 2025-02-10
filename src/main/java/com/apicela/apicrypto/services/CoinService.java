package com.apicela.apicrypto.services;

import com.apicela.apicrypto.dtos.Coin;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CoinService {
    private final WebClient webClient;

    public CoinService(ObjectMapper objectMapper) {
        // Configura o WebClient para usar o ObjectMapper personalizado
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> {
                    configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));
                    configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper));
                })
                .build();

        this.webClient = WebClient.builder()
                .baseUrl("https://api.coingecko.com/api/v3")
                .exchangeStrategies(strategies)
                .build();
    }

    public Flux<Coin> listAllCoins() {
        String COINS_ENDPOINT = "/coins/markets";
        String PARAMS = "?vs_currency=brl&price_change_percentage=1h,24h,7d,14d,30d,200d,1y";
        System.out.println("CACHED");
        return webClient.get()
                .uri(COINS_ENDPOINT + PARAMS)
                .retrieve()
                .bodyToFlux(Coin.class);
    }

    public Mono<Coin> getCoinById(String id) {
        return null;
    }


}