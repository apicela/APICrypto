package com.apicela.apicrypto.models;

import com.apicela.apicrypto.models.dtos.MonitoringDTO;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "monitorings")
public class Monitoring {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    UUID userId;
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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
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
