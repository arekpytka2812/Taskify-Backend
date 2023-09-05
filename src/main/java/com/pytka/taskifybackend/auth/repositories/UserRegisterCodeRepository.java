package com.pytka.taskifybackend.auth.repositories;

import com.pytka.taskifybackend.auth.models.UserRegisterCodeEntity;
import com.pytka.taskifybackend.core.abstraction.AbstractRepository;

import java.util.Optional;

public interface UserRegisterCodeRepository extends AbstractRepository<UserRegisterCodeEntity> {

    Optional<UserRegisterCodeEntity> findByEmail(String email);
}
