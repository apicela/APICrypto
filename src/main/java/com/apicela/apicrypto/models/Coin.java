package com.apicela.apicrypto.models;

import java.util.Date;


public record Coin(
        String id,
        String symbol,
        String name,
        String image,
        double currentPrice,
        long marketCap,
        int marketCapRank,
        long fullyDilutedValuation,
        long totalVolume,
        double high24h,
        double low24h,
        double priceChange24h,
        double priceChangePercentage24h,
        long marketCapChange24h,
        double marketCapChangePercentage24h,
        long circulatingSupply,
        long totalSupply,
        Long maxSupply, // Alterado para Long para permitir null
        double ath,
        double athChangePercentage,
        Date athDate, // Alterado para Date
        double atl,
        double atlChangePercentage,
        Date atlDate, // Alterado para Date
        Date lastUpdated, // Alterado para Date
        PriceChanges priceChanges
) {
}