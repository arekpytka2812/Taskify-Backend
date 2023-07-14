package com.pytka.taskifybackend.core.repositories;

import com.pytka.taskifybackend.core.abstraction.AbstractRepository;
import com.pytka.taskifybackend.core.models.UserEntity;

import java.util.Optional;

public interface UserRepository extends AbstractRepository<UserEntity> {
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
