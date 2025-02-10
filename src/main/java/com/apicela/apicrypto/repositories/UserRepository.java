package com.apicela.apicrypto.repositories;

import com.apicela.apicrypto.dtos.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserDTO, UUID> {

}