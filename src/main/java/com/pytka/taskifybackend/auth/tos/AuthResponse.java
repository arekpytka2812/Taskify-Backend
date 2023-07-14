package com.pytka.taskifybackend.auth.tos;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class AuthResponse {

    private String token;
}
