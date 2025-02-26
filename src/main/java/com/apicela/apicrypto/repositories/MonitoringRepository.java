package com.apicela.apicrypto.repositories;

import com.apicela.apicrypto.models.Monitoring;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MonitoringRepository extends JpaRepository<Monitoring, Long> {
    @Query("SELECT m FROM Monitoring m WHERE m.id = :id AND m.isDeleted = false")
    Optional<Monitoring> findByIdAndNotDeleted(@Param("id") Long id);

    @Query("SELECT m.id FROM Monitoring m WHERE m.isDeleted = false")
    List<Long> findAllIds();

    @Query("SELECT m.id FROM Monitoring m WHERE m.isDeleted = false AND m.coinId = :coinId")
    List<Long> findAllByCoinId(String coinId);
}