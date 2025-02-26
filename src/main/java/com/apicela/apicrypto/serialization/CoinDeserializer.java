package com.apicela.apicrypto.serialization;

import com.apicela.apicrypto.models.dtos.Coin;
import com.apicela.apicrypto.models.dtos.PriceChanges;
import com.apicela.apicrypto.utils.DateUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class CoinDeserializer extends JsonDeserializer<Coin> {
    @Override
    public Coin deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        var marketData = node.path("market_data");
        // Extract PriceChanges fields
        PriceChanges priceChanges = new PriceChanges(
                (float) marketData.path("price_change_percentage_1h_in_currency").path("brl").asDouble(),
                (float) marketData.path("price_change_percentage_24h_in_currency").path("brl").asDouble(),
                (float) marketData.path("price_change_percentage_7d_in_currency").path("brl").asDouble(),
                (float) marketData.path("price_change_percentage_14d_in_currency").path("brl").asDouble(),
                (float) marketData.path("price_change_percentage_30d_in_currency").path("brl").asDouble(),
                (float) marketData.path("price_change_percentage_200d_in_currency").path("brl").asDouble(),
                (float) marketData.path("price_change_percentage_1y_in_currency").path("brl").asDouble()
        );
        System.out.println("mk ath: " + marketData.path("ath_date"));
        // Extract Coin fields
        return new Coin(
                node.path("id").asText(),
                node.path("symbol").asText(),
                node.path("name").asText(),
                node.path("image").path("thumb").asText(),
                marketData.path("current_price").path("brl").asDouble(),
                marketData.path("market_cap").path("brl").asLong(),
                node.path("market_cap_rank").asInt(),
                marketData.path("fully_diluted_valuation").path("brl").asLong(),
                marketData.path("total_volume").path("brl").asLong(),
                marketData.path("high_24h").path("brl").asDouble(),
                marketData.path("low_24h").path("brl").asDouble(),
                marketData.path("price_change_24h").asDouble(),
                marketData.path("price_change_percentage_24h").asDouble(),
                marketData.path("market_cap_change_24h").asLong(),
                marketData.path("market_cap_change_percentage_24h").asDouble(),
                marketData.path("circulating_supply").asLong(),
                marketData.path("total_supply").asLong(),
                marketData.path("max_supply").isNull() ? null : marketData.path("max_supply").asLong(), // Trata max_supply como null se for nulo
                marketData.path("ath").path("brl").asDouble(),
                marketData.path("ath_change_percentage").path("brl").asDouble(),
                DateUtils.parseDate(marketData.path("ath_date").path("brl").asText()), // Trata ath_date como null se for nulo
                marketData.path("atl").path("brl").asDouble(),
                marketData.path("atl_change_percentage").path("brl").asDouble(),
                DateUtils.parseDate(marketData.path("atl_date").path("brl").asText()), // Trata ath_date como null se for nulo
                node.path("last_updated").isNull() ? null : DateUtils.parseDate(node.path("last_updated").asText()), // Trata last_updated como null se for nulo
                priceChanges
        );
    }
}

