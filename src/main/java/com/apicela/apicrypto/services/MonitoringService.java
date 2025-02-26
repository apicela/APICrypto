package com.apicela.apicrypto.services;

import com.apicela.apicrypto.exceptions.SaveException;
import com.apicela.apicrypto.models.Monitoring;
import com.apicela.apicrypto.models.dtos.Coin;
import com.apicela.apicrypto.models.dtos.Mail;
import com.apicela.apicrypto.models.dtos.MonitoringDTO;
import com.apicela.apicrypto.models.dtos.UserDTO;
import com.apicela.apicrypto.repositories.MonitoringRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoringService {
    MonitoringRepository monitoringRepository;
    UserService userService;

    public MonitoringService (MonitoringRepository monitoringRepository) {
        this.monitoringRepository = monitoringRepository;
    }

    public MonitoringDTO save (MonitoringDTO monitoringDTO){
        var monitoring = new Monitoring(monitoringDTO);
        try{
            Monitoring savedMonitoring = monitoringRepository.save(monitoring);
            return new MonitoringDTO(savedMonitoring.getUserId(), savedMonitoring.getCoinId(), savedMonitoring.getPrice(), savedMonitoring.isGreatherThan());
        } catch (Exception e) {
            throw new SaveException("Failed to save monitoring data", e);
        }
    }

    public void deleteById(Long id) {
        var monitoring = monitoringRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Record not found with ID: " + id));

        monitoring.setDeleted(true);
        monitoringRepository.save(monitoring);
    }

    @Cacheable(value = "cache512size", key = "#id")
    public MonitoringDTO findById(long id) {
        return monitoringRepository.findByIdAndNotDeleted(id)
                .map(monitoring -> new MonitoringDTO(
                        monitoring.getUserId(),
                        monitoring.getCoinId(),
                        monitoring.getPrice(),
                        monitoring.isGreatherThan()))
                .orElseThrow(() -> new EntityNotFoundException("Record not found with ID:  " + id));
    }

    public List<Long> getMonitoringIdsForCoin(String coinId) {
        return monitoringRepository.findAllByCoinId(coinId);
    }

    public Mail verifyConditionsToSendMail(MonitoringDTO monitoredItem, Coin coin) {
        boolean isHigher = monitoredItem.greatherThan() && coin.currentPrice() >= monitoredItem.price();
        boolean isLower = !monitoredItem.greatherThan() && coin.currentPrice() <= monitoredItem.price();
        if (isHigher || isLower) {
            UserDTO userToBeNotified = userService.findById(monitoredItem.userId());
            String title = "O Preço de" + coin.name() + " mudou!";
            String msg = "Olá, " + userToBeNotified.name() +"!\n" +
                    "O preço da moeda " + coin.name() + " alcançou seu preço de alerta!";
            return new Mail(userToBeNotified.mail(), title, msg);
        } else return null;
    }
}