package com.apicela.apicrypto.repositories;

import com.apicela.apicrypto.models.Monitoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoringRepository extends JpaRepository<Monitoring, Long> {

}