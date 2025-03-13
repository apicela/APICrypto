package com.apicela.apicrypto.services;

import com.apicela.apicrypto.exceptions.NotFoundException;
import com.apicela.apicrypto.exceptions.SaveException;
import com.apicela.apicrypto.exceptions.UpdateException;
import com.apicela.apicrypto.models.Monitoring;
import com.apicela.apicrypto.models.dtos.Coin;
import com.apicela.apicrypto.models.dtos.Mail;
import com.apicela.apicrypto.models.dtos.MonitoringDTO;
import com.apicela.apicrypto.models.dtos.UpdateMonitoringDTO;
import com.apicela.apicrypto.repositories.MonitoringRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Log4j2
public class MonitoringService {
    final UserService userService;
    MonitoringRepository monitoringRepository;

    public MonitoringService(MonitoringRepository monitoringRepository, UserService userService) {
        this.monitoringRepository = monitoringRepository;
        this.userService = userService;
    }

    public Mono<UpdateMonitoringDTO> save(MonitoringDTO monitoringDTO) {
        return userService.findById(monitoringDTO.userId())
                .flatMap(x -> {
                    if (!CoinService.coinsNameHashMap.containsKey(monitoringDTO.coinId())) {
                        return Mono.error(new NotFoundException("A moeda " + monitoringDTO.coinId() + " não existe."));
                    }
                    Monitoring m = new Monitoring(monitoringDTO);
                    m.setCoinId(CoinService.coinsNameHashMap.get(monitoringDTO.coinId()));
                    return monitoringRepository.save(m)
                            .doOnNext(savedMonitoring -> log.info("Monitoring saved {}", savedMonitoring))
                            .map(monitoring1 -> new UpdateMonitoringDTO(
                                    monitoring1.getCoinId(),
                                    monitoring1.getPrice(),
                                    monitoring1.isGreatherThan()
                            ))
                            .onErrorMap(e -> new SaveException("Failed to save monitoring data", e));
                });
    }

    public Mono<Void> deleteById(Long id) {
        return monitoringRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Monitoring not found with ID: " + id)))
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
                .switchIfEmpty(Mono.error(new NotFoundException("Monitoring not found with ID: " + id)));
    }


    public Flux<Long> getMonitoringIdsForCoin(String coinId) {
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
                .switchIfEmpty(Mono.error(new NotFoundException("Monitoring with ID " + id + " not found")))
                .flatMap(existing -> {
                    Monitoring m = new Monitoring(updateMonitoringDTO);
                    m.setId(id);
                    return monitoringRepository.save(m).then();
                })
                .onErrorMap(e -> new UpdateException("Failed to update monitoring data", e));
    }
}