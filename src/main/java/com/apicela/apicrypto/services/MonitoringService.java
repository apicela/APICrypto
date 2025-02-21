package com.apicela.apicrypto.services;

import com.apicela.apicrypto.exceptions.SaveException;
import com.apicela.apicrypto.models.Monitoring;
import com.apicela.apicrypto.models.dtos.MonitoringDTO;
import com.apicela.apicrypto.repositories.MonitoringRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MonitoringService {
    MonitoringRepository monitoringRepository;
    Map<String, List<Long>> monitoringMap = new ConcurrentHashMap<>();

    public MonitoringService (MonitoringRepository monitoringRepository) {
        this.monitoringRepository = monitoringRepository;
    }

    public MonitoringDTO save (MonitoringDTO monitoringDTO){
        var monitoring = new Monitoring(monitoringDTO);
        try{
            Monitoring savedMonitoring = monitoringRepository.save(monitoring);
            monitoringMap.computeIfAbsent(monitoring.getCoinId(), k -> new ArrayList<>()).add(monitoring.getId());
            return new MonitoringDTO(savedMonitoring.getUserId(), savedMonitoring.getCoinId(), savedMonitoring.getPercentageDifference());
        } catch (Exception e) {
            throw new SaveException("Failed to save monitoring data", e);
        }
    }

    @Cacheable(value = "cache512size", key = "'monitoringsKey'")
    public Optional<MonitoringDTO> findById (long id){
        return monitoringRepository.findById(id)
                .map(monitoring -> new MonitoringDTO(
                        monitoring.getUserId(),
                        monitoring.getCoinId(),
                        monitoring.getPrice(),
                        monitoring.isGreatherThan()));
    }

    public List<Long> getMonitoringIdsForCoin(String coinId) {
        return monitoringMap.getOrDefault(coinId, Collections.emptyList());
    }
}