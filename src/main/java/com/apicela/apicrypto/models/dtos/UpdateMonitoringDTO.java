package com.apicela.apicrypto.models.dtos;

public record UpdateMonitoringDTO(
         String coinId,
         Double price,
         Boolean greatherThan) { }
