package com.pytka.taskifybackend.auth.TO;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class AuthResponse {

    private Long ID;

    private String token;
}
