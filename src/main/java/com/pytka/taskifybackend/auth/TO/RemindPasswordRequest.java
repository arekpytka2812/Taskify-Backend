package com.pytka.taskifybackend.auth.TO;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@RequiredArgsConstructor
public class RemindPasswordRequest {

    private String email;
}
