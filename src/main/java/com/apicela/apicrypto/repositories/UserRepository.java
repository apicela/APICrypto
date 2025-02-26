package com.apicela.apicrypto.repositories;

import com.apicela.apicrypto.models.Monitoring;
import com.apicela.apicrypto.models.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT u FROM User u WHERE u.id = :id AND u.isDeleted = false")
    Optional<Monitoring> findByIdAndNotDeleted(@Param("id") Long id);

}