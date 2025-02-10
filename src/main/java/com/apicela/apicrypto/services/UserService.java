package com.apicela.apicrypto.services;

import com.apicela.apicrypto.dtos.MonitoringDTO;
import com.apicela.apicrypto.dtos.UserDTO;
import com.apicela.apicrypto.exceptions.SaveException;
import com.apicela.apicrypto.models.Monitoring;
import com.apicela.apicrypto.models.User;
import com.apicela.apicrypto.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    UserRepository userRepository;

    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO save (UserDTO monitoringDTO){
        var user = new User(monitoringDTO);
        try{
            User savedUser = userRepository.save(user);
            return new UserDTO(savedUser.getName(), savedUser.getLastName(), savedUser.getMail());
        } catch (Exception e) {
            throw new SaveException("Failed to save user data", e);
        }
    }

    public Optional<UserDTO> findById (UUID id){
        return userRepository.findById(id)
                .map(user -> new UserDTO(
                        user.getName(),
                        user.getLastName(),
                        user.getMail()
                ));
    }
}