package com.pytka.taskifybackend.auth.models;


import com.pytka.taskifybackend.core.abstraction.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@RequiredArgsConstructor
@Table(name = "USER_REGISTER_CODE")
@Entity
public class UserVerificationCodeEntity extends AbstractEntity {

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "code")
    private String code;

    @Column(name = "expirationDate")
    private LocalDateTime expirationDate;

}
