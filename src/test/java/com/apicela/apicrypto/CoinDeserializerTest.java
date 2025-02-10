package com.apicela.apicrypto;

import com.apicela.apicrypto.dtos.Coin;
import com.apicela.apicrypto.serialization.CoinDeserializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CoinDeserializerTest {

    private ObjectMapper objectMapper;

    @BeforeEach   // Alterado para @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Coin.class, new CoinDeserializer());
        objectMapper.registerModule(module);
    }

    @Test   // Teste com JUnit 5
    public void testDeserializeCoin() throws JsonProcessingException {
        String json = "{\"id\": \"bitcoin\", \"symbol\": \"btc\", \"name\": \"Bitcoin\", \"image\": \"https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400\", \"current_price\": 569452, \"market_cap\": 11284399901335, \"market_cap_rank\": 1, \"fully_diluted_valuation\": 11284399901335, \"total_volume\": 276070147208, \"high_24h\": 581458, \"low_24h\": 552515, \"price_change_24h\": -1610.9789831486996, \"price_change_percentage_24h\": -0.2821, \"market_cap_change_24h\": -26787932297.941406, \"market_cap_change_percentage_24h\": -0.23683, \"circulating_supply\": 19820059.0, \"total_supply\": 19820059.0, \"max_supply\": 21000000.0, \"ath\": 672350, \"ath_change_percentage\": -15.85531, \"ath_date\": \"2024-12-26T00:20:46.738Z\", \"atl\": 149.66, \"atl_change_percentage\": 377924.91357, \"atl_date\": \"2013-07-05T00:00:00.000Z\", \"roi\": null, \"last_updated\": \"2025-02-05T17:04:45.396Z\", \"price_change_percentage_1h_in_currency\": 0.43514968461932496, \"price_change_percentage_1y_in_currency\": 166.6267788389245, \"price_change_percentage_14d_in_currency\": 166.6267788389245, \"price_change_percentage_200d_in_currency\": 52.10368586448841, \"price_change_percentage_24h_in_currency\": -0.2821017219976728, \"price_change_percentage_30d_in_currency\": -8.756154207514601, \"price_change_percentage_7d_in_currency\": -5.148417375941318}";

        Coin coin = objectMapper.readValue(json, Coin.class);

        System.out.println("Coin: " + coin);
        assertNotNull(coin);
        assertEquals("bitcoin", coin.id());
    }
}
