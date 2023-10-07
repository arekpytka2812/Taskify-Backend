package com.pytka.taskifybackend.auth.TO;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@RequiredArgsConstructor
public class ForgottenPasswordRequest {

    private String email;
    private String password;
    private String verificationCode;
    private LocalDateTime sentDate;
}
