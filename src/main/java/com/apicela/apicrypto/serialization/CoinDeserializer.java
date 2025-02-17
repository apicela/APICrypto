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

        // Extract PriceChanges fields
        PriceChanges priceChanges = new PriceChanges(
                (float) node.path("price_change_percentage_1h_in_currency").asDouble(),
                (float) node.path("price_change_percentage_24h_in_currency").asDouble(),
                (float) node.path("price_change_percentage_7d_in_currency").asDouble(),
                (float) node.path("price_change_percentage_14d_in_currency").asDouble(),
                (float) node.path("price_change_percentage_30d_in_currency").asDouble(),
                (float) node.path("price_change_percentage_200d_in_currency").asDouble(),
                (float) node.path("price_change_percentage_1y_in_currency").asDouble()
        );

        // Extract Coin fields
        return new Coin(
                node.path("id").asText(),
                node.path("symbol").asText(),
                node.path("name").asText(),
                node.path("image").asText(),
                node.path("current_price").asDouble(),
                node.path("market_cap").asLong(),
                node.path("market_cap_rank").asInt(),
                node.path("fully_diluted_valuation").asLong(),
                node.path("total_volume").asLong(),
                node.path("high_24h").asDouble(),
                node.path("low_24h").asDouble(),
                node.path("price_change_24h").asDouble(),
                node.path("price_change_percentage_24h").asDouble(),
                node.path("market_cap_change_24h").asLong(),
                node.path("market_cap_change_percentage_24h").asDouble(),
                node.path("circulating_supply").asLong(),
                node.path("total_supply").asLong(),
                node.path("max_supply").isNull() ? null : node.path("max_supply").asLong(), // Trata max_supply como null se for nulo
                node.path("ath").asDouble(),
                node.path("ath_change_percentage").asDouble(),
                node.path("ath_date").isNull() ? null : DateUtils.parseDate(node.path("ath_date").asText()), // Trata ath_date como null se for nulo
                node.path("atl").asDouble(),
                node.path("atl_change_percentage").asDouble(),
                node.path("atl_date").isNull() ? null : DateUtils.parseDate(node.path("atl_date").asText()), // Trata atl_date como null se for nulo
                node.path("last_updated").isNull() ? null : DateUtils.parseDate(node.path("last_updated").asText()), // Trata last_updated como null se for nulo
                priceChanges
        );
    }
}

