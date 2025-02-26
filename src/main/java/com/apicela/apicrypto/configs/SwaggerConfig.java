package com.apicela.apicrypto.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@OpenAPIDefinition
public class SwaggerConfig {
    @Bean
    public OpenAPI api() {
        return new OpenAPI().
                components(new Components())
                .info(new Info().title("API CRYPTO").version("1.0.0")
                        .description("REST API documentation for cryptomoedas from Coin Gecko API"));
    }
}