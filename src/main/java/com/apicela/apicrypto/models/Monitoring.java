package com.apicela.apicrypto.models;

public class Monitoring {
    long userId;
    String coinId;
    float percentageDifference;

    public Monitoring(long userId, String coinId, float percentageDifference) {
        this.userId = userId;
        this.coinId = coinId;
        this.percentageDifference = percentageDifference;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    public float getPercentageDifference() {
        return percentageDifference;
    }

    public void setPercentageDifference(float percentageDifference) {
        this.percentageDifference = percentageDifference;
    }
}
