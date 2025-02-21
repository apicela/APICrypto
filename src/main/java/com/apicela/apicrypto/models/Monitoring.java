package com.apicela.apicrypto.models;

import com.apicela.apicrypto.models.dtos.MonitoringDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "monitorings")
public class Monitoring {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    long userId;
    String coinId;
    double price;
    boolean greatherThan;

    public Monitoring(MonitoringDTO monitoringDTO) {
        this.userId = monitoringDTO.userId();
        this.coinId = monitoringDTO.coinId();
        this.price = monitoringDTO.price();
        this.greatherThan = monitoringDTO.greatherThan();
    }

    public Monitoring() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isGreatherThan() {
        return greatherThan;
    }

    public void setGreatherThan(boolean greatherThan) {
        this.greatherThan = greatherThan;
    }
}
