package com.apicela.apicrypto.models;

import com.apicela.apicrypto.dtos.MonitoringDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "monitorings")
public class Monitoring {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    long userId;
    String coinId;
    float percentageDifference;

    public Monitoring(MonitoringDTO monitoringDTO) {
        this.userId = monitoringDTO.userId();
        this.coinId = monitoringDTO.coinId();
        this.percentageDifference = monitoringDTO.percentageDifference();
    }

    public Monitoring() {}

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
