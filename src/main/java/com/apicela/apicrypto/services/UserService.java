package com.apicela.apicrypto.services;

import com.apicela.apicrypto.exceptions.SaveException;
import com.apicela.apicrypto.models.User;
import com.apicela.apicrypto.models.dtos.UserDTO;
import com.apicela.apicrypto.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<UserDTO> save(UserDTO userDTO) {
        var user = new User(userDTO);
        return userRepository.save(user)
                .map(savedUser -> new UserDTO(savedUser.getName(), savedUser.getLastName(), savedUser.getMail()))
                .onErrorMap(e -> new SaveException("Failed to save user data", e));
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