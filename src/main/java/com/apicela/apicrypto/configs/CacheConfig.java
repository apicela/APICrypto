package com.apicela.apicrypto.configs;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {
    private static RedisCacheConfiguration defaultCacheConfig(long timeout, TimeUnit unit) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.of(timeout, unit.toChronoUnit()))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultCacheConfig(10, TimeUnit.MINUTES)) // Padrão: 10 min
                .withCacheConfiguration("cache10Min",
                        defaultCacheConfig(10, TimeUnit.MINUTES)) // Expiração de 10 min
                .withCacheConfiguration("cache1Hora",
                        defaultCacheConfig(1, TimeUnit.HOURS)) // Expiração de 1 hora
                .withCacheConfiguration("cache1Dia",
                        defaultCacheConfig(1, TimeUnit.DAYS)) // Expiração de 1 dia
                .build();
    }
}
