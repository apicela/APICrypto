package com.apicela.apicrypto.schedulers;

import com.apicela.apicrypto.services.CoinService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CoinScheduler {

    private final CoinService coinService;

    public CoinScheduler(CoinService coinService) {
        this.coinService = coinService;
    }

    @Scheduled(cron = "0 */15 8-23 * * *")
    public void updateCoinsCache() {
        coinService.listAllCoins().subscribe();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void preloadCacheOnStartup() {
        updateCoinsCache();
    }
}
