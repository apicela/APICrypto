package com.apicela.apicrypto.configs;

import com.apicela.apicrypto.dtos.Coin;
import com.apicela.apicrypto.serialization.CoinDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Coin.class, new CoinDeserializer());
        objectMapper.registerModule(module);
        objectMapper.registerModule(new JavaTimeModule()); // Habilita suporte a LocalDateTime

        return objectMapper;
    }
}