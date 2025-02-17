package com.apicela.apicrypto.services;

import com.apicela.apicrypto.exceptions.SaveException;
import com.apicela.apicrypto.models.Monitoring;
import com.apicela.apicrypto.models.dtos.MonitoringDTO;
import com.apicela.apicrypto.repositories.MonitoringRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MonitoringService {
    MonitoringRepository monitoringRepository;

    public MonitoringService (MonitoringRepository monitoringRepository) {
        this.monitoringRepository = monitoringRepository;
    }

    public MonitoringDTO save (MonitoringDTO monitoringDTO){
        var monitoring = new Monitoring(monitoringDTO);
        try{
            Monitoring savedMonitoring = monitoringRepository.save(monitoring);
            return new MonitoringDTO(savedMonitoring.getUserId(), savedMonitoring.getCoinId(), savedMonitoring.getPercentageDifference());
        } catch (Exception e) {
            throw new SaveException("Failed to save monitoring data", e);
        }
    }

    public Optional<MonitoringDTO> findById (long id){
        return monitoringRepository.findById(id)
                .map(monitoring -> new MonitoringDTO(
                        monitoring.getUserId(),
                        monitoring.getCoinId(),
                        monitoring.getPercentageDifference()
                ));
    }
}