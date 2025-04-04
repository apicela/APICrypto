package com.apicela.apicrypto.repositories;

import com.apicela.apicrypto.models.UserModel;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserModel, UUID> {
    @Query("SELECT * FROM users WHERE id = :id AND is_deleted = false")
    Mono<UserModel> findByIdAndNotDeleted(@Param("id") UUID id);

    @Query("SELECT * FROM users WHERE mail = :mail AND is_deleted = false")
    Mono<UserModel> findByMail(@Param("mail") String mail);

    Mono<UserModel> findByMailAndIsDeletedFalse(String mail);

}