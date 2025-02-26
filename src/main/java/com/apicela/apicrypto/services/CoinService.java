package com.apicela.apicrypto.services;

import com.apicela.apicrypto.models.dtos.Coin;
import com.apicela.apicrypto.models.dtos.Mail;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class CoinService {
    private final WebClient webClient;
    private final MonitoringService monitoringService;
    private final MailService mailService;

    public CoinService(ObjectMapper objectMapper, MonitoringService monitoringService, MailService mailService) {
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

        this.monitoringService = monitoringService;
        this.mailService = mailService;
    }

    @Cacheable(value = "cache10Min", key = "'listAllCoinsCache'", sync = true)
    @Scheduled(cron = "0 */15 8-23 * * *")
    public Flux<Coin> listAllCoins() {
        String COINS_ENDPOINT = "/coins/markets";
        String PARAMS = "?vs_currency=brl&price_change_percentage=1h,24h,7d,14d,30d,200d,1y&localization=false&developer_data=false";
        log.info("Valores das criptomoedas atualizadas na cache. {}", LocalDateTime.now());
        return webClient.get()
                .uri(COINS_ENDPOINT + PARAMS)
                .retrieve()
                .bodyToFlux(Coin.class)
                .doOnNext(this::checkAndNotify);
    }

    private void checkAndNotify(Coin coin) {
        var monitoredItemsList = monitoringService.getMonitoringIdsForCoin(coin.id());
        List<Mail> usersToSendMail = new ArrayList<>();
        if (!monitoredItemsList.isEmpty()) {
            for (Long id : monitoredItemsList) {
                var monitoredItem = monitoringService.findById(id);
                Mail notify = monitoringService.verifyConditionsToSendMail(monitoredItem, coin);
                if(notify != null) usersToSendMail.add(notify);
            }
        }
        mailService.sendMultipleMails(usersToSendMail);
    }

    @Cacheable(value = "cache1Min", key = "#name", sync = true)
    public Mono<Coin> findById(String name) {
        String ENDPOINT = "/coins/" + name;
        log.info("Valor da criptomoeda {} atualizada na cache. {}", name, LocalDateTime.now());
        return webClient.get()
                .uri(ENDPOINT)
                .retrieve()
                .bodyToMono(Coin.class);
    }
}