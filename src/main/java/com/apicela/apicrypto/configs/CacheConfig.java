package com.apicela.apicrypto.configs;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CaffeineCacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setAsyncCacheMode(true); // Habilitar modo assíncrono globalmente

        cacheManager.registerCustomCache("cache10Min", cache10Min().buildAsync());
        cacheManager.registerCustomCache("cache1Min", cache1Min().buildAsync());
        cacheManager.registerCustomCache("cache512size", cache512entrys().buildAsync());

        return cacheManager;
    }

    @Bean
    public Caffeine<Object, Object> cache512entrys() {
        return Caffeine.newBuilder()
                .maximumSize(512);
    }

    @Bean
    public Caffeine<Object, Object> cache10Min() {
        return Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(1000)
                .removalListener((key, value, cause) -> {
                    if (cause == com.github.benmanes.caffeine.cache.RemovalCause.EXPIRED) {
                        System.out.println("Cache de 10 minutos expirado - chave: " + key + ", valor: " + value);
                    }
                });
    }

    @Bean
    public Caffeine<Object, Object> cache1Min() {
        return Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(20)
                .removalListener((key, value, cause) -> {
                    if (cause == com.github.benmanes.caffeine.cache.RemovalCause.EXPIRED) {
                        System.out.println("Cache de 10 minutos expirado - chave: " + key + ", valor: " + value);
                    }
                });
    }
}
