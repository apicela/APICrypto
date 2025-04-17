package com.apicela.apicrypto.services;

import com.apicela.apicrypto.exceptions.EmailAlreadyInUseException;
import com.apicela.apicrypto.exceptions.NotFoundException;
import com.apicela.apicrypto.exceptions.SaveException;
import com.apicela.apicrypto.models.UserModel;
import com.apicela.apicrypto.models.dtos.UserDTO;
import com.apicela.apicrypto.models.requests.RegisterUserDTO;
import com.apicela.apicrypto.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Log4j2
public class UserService implements ReactiveUserDetailsService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<UserDTO> save(RegisterUserDTO userDTO) {
        return findByMail(userDTO.email())
                .flatMap(existingUser -> Mono.<UserDTO>error(new EmailAlreadyInUseException("E-email already in use")))
                .switchIfEmpty(
                        userRepository.save(new UserModel(userDTO))
                                .doOnNext(savedUserModel -> log.info("User saved: {}", savedUserModel))
                                .map(savedUserModel -> new UserDTO(savedUserModel.getName(), savedUserModel.getLastName(), savedUserModel.getEmail()))
                                .onErrorMap(e -> new SaveException("Failed to save user data", e))

                );
    }

    public Mono<UserDTO> findById(UUID id) {
        return userRepository.findById(id)
                .map(userModel -> new UserDTO(
                        userModel.getName(),
                        userModel.getLastName(),
                        userModel.getEmail()
                ))
                .switchIfEmpty(Mono.error(new NotFoundException("User not found with ID: " + id)));
    }

    public Mono<UserDTO> findByMail(String mail) {
        return userRepository.findByEmail(mail)
                .map(userModel -> new UserDTO(
                        userModel.getName(),
                        userModel.getLastName(),
                        userModel.getEmail()
                ));
    }

    public Mono<Void> deleteById(UUID id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Record not found with ID: " + id)))
                .flatMap(userModel -> {
                    userModel.setDeleted(true);
                    return userRepository.save(userModel);
                })
                .then();
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByEmail(username).cast(UserDetails.class);
    }
}