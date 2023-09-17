package com.pytka.taskifybackend.auth.repositories;

import com.pytka.taskifybackend.auth.models.UserVerificationCodeEntity;
import com.pytka.taskifybackend.core.abstraction.AbstractRepository;

import java.util.Optional;

public interface UserVerificationCodeRepository extends AbstractRepository<UserVerificationCodeEntity> {

    Optional<UserVerificationCodeEntity> findByEmail(String email);
}
