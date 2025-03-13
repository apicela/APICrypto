package com.apicela.apicrypto.repositories;

import com.apicela.apicrypto.models.Monitoring;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface MonitoringRepository extends ReactiveCrudRepository<Monitoring, Long> {
    @Query("SELECT m FROM Monitoring m WHERE m.id = :id AND m.isDeleted = false")
    Mono<Monitoring> findByIdAndNotDeleted(@Param("id") Long id);

    @Query("SELECT m.id FROM Monitoring m WHERE m.isDeleted = false")
    Flux<List<Long>> findAllIds();

    @Query("SELECT m.id FROM Monitoring m WHERE m.isDeleted = false AND m.coinId = :coinId")
    Flux<List<Long>> findAllByCoinId(String coinId);
}