package com.apicela.apicrypto.repositories;

import com.apicela.apicrypto.models.Monitoring;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MonitoringRepository extends ReactiveCrudRepository<Monitoring, Long> {
    @Query("SELECT * FROM monitorings  WHERE id = :id AND is_deleted = false")
    Mono<Monitoring> findByIdAndNotDeleted(@Param("id") Long id);

    @Query("SELECT id FROM monitorings WHERE is_deleted = false")
    Flux<Long> findAllIds();

    @Query("SELECT id FROM monitorings WHERE is_deleted = false AND coin_id = :coinId")
    Flux<Long> findAllByCoinId(String coinId);
}