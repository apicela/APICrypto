package com.apicela.apicrypto.services;

import com.apicela.apicrypto.exceptions.SaveException;
import com.apicela.apicrypto.exceptions.UpdateException;
import com.apicela.apicrypto.models.Monitoring;
import com.apicela.apicrypto.models.dtos.Coin;
import com.apicela.apicrypto.models.dtos.Mail;
import com.apicela.apicrypto.models.dtos.MonitoringDTO;
import com.apicela.apicrypto.models.dtos.UpdateMonitoringDTO;
import com.apicela.apicrypto.repositories.MonitoringRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class MonitoringService {
    MonitoringRepository monitoringRepository;
    UserService userService;

    public MonitoringService(MonitoringRepository monitoringRepository) {
        this.monitoringRepository = monitoringRepository;
    }

    public Mono<MonitoringDTO> save(MonitoringDTO monitoringDTO) {
        var monitoring = new Monitoring(monitoringDTO);
        return monitoringRepository.save(monitoring)
                .map(monitoring1 -> new MonitoringDTO(
                        monitoring1.getUserId(),
                        monitoring1.getCoinId(),
                        monitoring1.getPrice(),
                        monitoring1.isGreatherThan()
                ))
                .onErrorMap(e -> new SaveException("Failed to save user data", e));
    }

    public Mono<Void> deleteById(Long id) {
        return monitoringRepository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Record not found with ID: " + id)))
                .flatMap(monitoring -> {
                    monitoring.setDeleted(true);
                    return monitoringRepository.save(monitoring);
                }).then();
    }

    @Cacheable(value = "cache512size", key = "#id")
    public Mono<MonitoringDTO> findById(long id) {
        return monitoringRepository.findByIdAndNotDeleted(id)
                .map(monitoring -> new MonitoringDTO(
                        monitoring.getUserId(),
                        monitoring.getCoinId(),
                        monitoring.getPrice(),
                        monitoring.isGreatherThan()))
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Record not found with ID: " + id)));
    }

    public List<Long> getMonitoringIdsForCoin(String coinId) {
        return monitoringRepository.findAllByCoinId(coinId);
    }

    public Mono<Mail> verifyConditionsToSendMail(MonitoringDTO monitoredItem, Coin coin) {
        boolean isHigher = monitoredItem.greatherThan() && coin.currentPrice() >= monitoredItem.price();
        boolean isLower = !monitoredItem.greatherThan() && coin.currentPrice() <= monitoredItem.price();
        if (isHigher || isLower) {
            return userService.findById(monitoredItem.userId())
                    .map(userToBeNotified -> {
                        String title = "O Preço de " + coin.name() + " mudou!";
                        String msg = "Olá, " + userToBeNotified.name() + "!\n" +
                                "O preço da moeda " + coin.name() + " alcançou seu preço de alerta!";
                        return new Mail(userToBeNotified.mail(), title, msg);
                    });
        } else {
            return Mono.empty();
        }
    }

    public Mono<Void> update(long id, UpdateMonitoringDTO updateMonitoringDTO) {
        return monitoringRepository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Monitoring with ID " + id + " not found")))
                .flatMap(existing -> {
                    Monitoring m = new Monitoring(updateMonitoringDTO);
                    m.setId(id);
                    return monitoringRepository.save(m).then();
                })
                .onErrorMap(e -> new UpdateException("Failed to update monitoring data", e));    }
}