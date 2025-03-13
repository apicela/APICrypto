package com.apicela.apicrypto.utils;

public class CoinApiParams {
    private String vsCurrency;
    private String priceChangePercentage;
    private boolean localization;
    private boolean developerData;

    public CoinApiParams() {
        this.vsCurrency = "brl";
        this.priceChangePercentage = "1h,24h,7d,14d,30d,200d,1y";
        this.localization = false;
        this.developerData = false;
    }

    public CoinApiParams(String vsCurrency, String priceChangePercentage, boolean localization, boolean developerData) {
        this.vsCurrency = vsCurrency;
        this.priceChangePercentage = priceChangePercentage;
        this.localization = localization;
        this.developerData = developerData;
    }

    public String getVsCurrency() {
        return vsCurrency;
    }

    public void setVsCurrency(String vsCurrency) {
        this.vsCurrency = vsCurrency;
    }

    public String getPriceChangePercentage() {
        return priceChangePercentage;
    }

    public void setPriceChangePercentage(String priceChangePercentage) {
        this.priceChangePercentage = priceChangePercentage;
    }

    public boolean isLocalization() {
        return localization;
    }

    public void setLocalization(boolean localization) {
        this.localization = localization;
    }

    public boolean isDeveloperData() {
        return developerData;
    }

    public void setDeveloperData(boolean developerData) {
        this.developerData = developerData;
    }

    public String toUrl(String urlBase) {
        StringBuilder url = new StringBuilder(urlBase + "?");
        url.append("vs_currency=").append(vsCurrency)
                .append("&price_change_percentage=").append(priceChangePercentage)
                .append("&localization=").append(localization ? "true" : "false")
                .append("&developer_data=").append(developerData ? "true" : "false");
        return url.toString();
    }
}

