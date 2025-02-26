package com.apicela.apicrypto.services;

import com.apicela.apicrypto.exceptions.SaveException;
import com.apicela.apicrypto.models.User;
import com.apicela.apicrypto.models.dtos.UserDTO;
import com.apicela.apicrypto.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

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

    public UserDTO findById (UUID id){
        return userRepository.findById(id)
                .map(user -> new UserDTO(
                        user.getName(),
                        user.getLastName(),
                        user.getMail()
                ))
                .orElseThrow(() -> new EntityNotFoundException("Record not found with ID: " + id));
    }

    public void deleteById(UUID id) {
        var monitoring = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Record not found with ID: " + id));

        monitoring.setDeleted(true);
        userRepository.save(monitoring);
    }

}