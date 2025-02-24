package com.apicela.apicrypto.services;

import com.apicela.apicrypto.exceptions.SaveException;
import com.apicela.apicrypto.models.Monitoring;
import com.apicela.apicrypto.models.User;
import com.apicela.apicrypto.models.dtos.Coin;
import com.apicela.apicrypto.models.dtos.Mail;
import com.apicela.apicrypto.models.dtos.MonitoringDTO;
import com.apicela.apicrypto.models.dtos.UserDTO;
import com.apicela.apicrypto.repositories.MonitoringRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MonitoringService {
    MonitoringRepository monitoringRepository;
    UserService userService;
    Map<String, List<Long>> monitoringMap = new ConcurrentHashMap<>();

    public MonitoringService (MonitoringRepository monitoringRepository) {
        this.monitoringRepository = monitoringRepository;
    }

    public MonitoringDTO save (MonitoringDTO monitoringDTO){
        var monitoring = new Monitoring(monitoringDTO);
        try{
            Monitoring savedMonitoring = monitoringRepository.save(monitoring);
            monitoringMap.computeIfAbsent(monitoring.getCoinId(), k -> new ArrayList<>()).add(monitoring.getId());
            return new MonitoringDTO(savedMonitoring.getUserId(), savedMonitoring.getCoinId(), savedMonitoring.getPrice(), savedMonitoring.isGreatherThan());
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

    public Mail verifyConditionsToSendMail(MonitoringDTO monitoredItem, Coin coin) {
        boolean isHigher = monitoredItem.greatherThan() && coin.currentPrice() >= monitoredItem.price();
        boolean isLower = !monitoredItem.greatherThan() && coin.currentPrice() <= monitoredItem.price();
        if (isHigher || isLower) {
            UserDTO userToBeNotified = userService.findById(monitoredItem.userId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado para o ID: " + monitoredItem.userId()));
            String title = "O Preço de" + coin.name() + " mudou!";
            String msg = "Olá, " + userToBeNotified.name() +"!\n" +
                    "O preço da moeda " + coin.name() + " alcançou seu preço de alerta!";
            return new Mail(userToBeNotified.mail(), title, msg);
        } else return null;
    }
}