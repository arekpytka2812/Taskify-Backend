package com.pytka.taskifybackend.auth.repository;

import com.pytka.taskifybackend.auth.model.UserVerificationCodeEntity;
import com.pytka.taskifybackend.core.abstraction.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserVerificationCodeRepository extends AbstractRepository<UserVerificationCodeEntity> {

    Optional<UserVerificationCodeEntity> findByEmail(String email);

    @Query( "select e.ID " +
            "from UserVerificationCodeEntity e " +
            "where e.expirationDate < :now ")
    List<Long> getExpiredVerificationCodeIDS(
            @Param("now") LocalDateTime now
    );
}
