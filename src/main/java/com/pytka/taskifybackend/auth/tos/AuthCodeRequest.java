package com.pytka.taskifybackend.auth.tos;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
public class AuthCodeRequest {

    private String email;

    private String username;

    private String authCode;

    private LocalDateTime sentRequestDate;

}
