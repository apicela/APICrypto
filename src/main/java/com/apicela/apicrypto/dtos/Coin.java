package com.apicela.apicrypto.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        Long maxSupply,
        double ath,
        double athChangePercentage,
        LocalDateTime athDate,
        double atl,
        double atlChangePercentage,
        LocalDateTime atlDate,
        LocalDateTime lastUpdated,
        PriceChanges priceChanges
) {
}