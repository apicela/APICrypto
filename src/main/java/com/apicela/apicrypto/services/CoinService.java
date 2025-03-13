package com.apicela.apicrypto.services;

import com.apicela.apicrypto.models.dtos.Coin;
import com.apicela.apicrypto.utils.CoinApiParams;
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
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

@Service
@Log4j2
public class CoinService {
    private final WebClient webClient;
    private final MonitoringService monitoringService;
    private final MailService mailService;
    private final CoinApiParams coinApiParams = new CoinApiParams();

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
        log.info("Valores das criptomoedas atualizadas na cache. {}", LocalDateTime.now());
        return webClient.get()
                .uri(coinApiParams.toUrl(COINS_ENDPOINT))
                .retrieve()
                .bodyToFlux(Coin.class)
                .doOnNext(this::checkAndNotify);
    }

    private Mono<Void> checkAndNotify(Coin coin) {
        return monitoringService.getMonitoringIdsForCoin(coin.id())
                .flatMapIterable(monitoredItemsList -> monitoredItemsList)
                .flatMap(id -> monitoringService.findById(id)
                        .publishOn(Schedulers.boundedElastic())
                        .flatMap(it -> monitoringService.verifyConditionsToSendMail(it, coin))
                        .filter(mail -> mail != null))
                .collectList()
                .flatMap(mails -> mailService.sendMultipleMails(mails).then());
    }

    @Cacheable(value = "cache1Min", key = "#name", sync = true)
    public Mono<Coin> findById(String name) {
        String ENDPOINT = "/coins/" + name;
        log.info("Valor da criptomoeda {} atualizada na cache. {}", name, LocalDateTime.now());
        return webClient.get()
                .uri(coinApiParams.toUrl(ENDPOINT))
                .retrieve()
                .bodyToMono(Coin.class)
                .doOnNext(this::checkAndNotify);
    }
}