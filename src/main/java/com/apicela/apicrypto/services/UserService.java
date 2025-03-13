package com.apicela.apicrypto.services;

import com.apicela.apicrypto.exceptions.EmailAlreadyInUseException;
import com.apicela.apicrypto.exceptions.EntityNotFoundException;
import com.apicela.apicrypto.exceptions.SaveException;
import com.apicela.apicrypto.models.User;
import com.apicela.apicrypto.models.dtos.UserDTO;
import com.apicela.apicrypto.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Log4j2
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<UserDTO> save(UserDTO userDTO) {
        var user = new User(userDTO);
        return userRepository.findByMail(user.getMail())
                .flatMap(existingUser -> Mono.<UserDTO>error(new EmailAlreadyInUseException("E-mail already in use")))
                .switchIfEmpty(
                        userRepository.save(user)
                                .doOnNext(savedUser -> log.info("User saved: {}", savedUser))
                                .map(savedUser -> new UserDTO(savedUser.getName(), savedUser.getLastName(), savedUser.getMail()))
                                .onErrorMap(e -> new SaveException("Failed to save user data", e))

                );
    }


    public Mono<UserDTO> findById(UUID id) {
        return userRepository.findById(id)
                .map(user -> new UserDTO(
                        user.getName(),
                        user.getLastName(),
                        user.getMail()
                ))
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Record not found with ID: " + id)));
    }

    public Mono<UserDTO> findByMail(String mail) {
        return userRepository.findByMail(mail)
                .map(user -> new UserDTO(
                        user.getName(),
                        user.getLastName(),
                        user.getMail()
                ));
    }

    public Mono<Void> deleteById(UUID id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Record not found with ID: " + id)))
                .flatMap(user -> {
                    user.setDeleted(true);
                    return userRepository.save(user);
                })
                .then();  // Converte o Mono<User> para Mono<Void>, pois o retorno da operação não precisa do valor
    }


}