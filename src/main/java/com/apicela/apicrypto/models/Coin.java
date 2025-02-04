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
double circulatingSupply,
double totalSupply,
Double maxSupply,
double ath,
double athChangePercentage,
Date athDate,
double atl,
double atlChangePercentage,
Date atlDate,
Date lastUpdated,
PriceChanges priceChanges){}