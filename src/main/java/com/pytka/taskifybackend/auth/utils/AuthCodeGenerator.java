package com.pytka.taskifybackend.auth.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class AuthCodeGenerator {

    private final PasswordEncoder encoder;

    public String generateCode(String email){

        String code = encoder.encode(email);

        return code.substring(8,14);
    }

}
