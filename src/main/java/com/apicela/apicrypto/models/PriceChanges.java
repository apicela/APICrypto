package com.apicela.apicrypto.models;


public record PriceChanges(
        float priceChangePercentage1h,
        float priceChangePercentage24h,
        float priceChangePercentage7d,
        float priceChangePercentage14d,
        float priceChangePercentage30d,
        float priceChangePercentage200d,
        float priceChangePercentage1y
) {
}