package com.pytka.taskifybackend.email.TOs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@RequiredArgsConstructor
@SuperBuilder
@Getter
@Setter
public class AuthEmailTO extends EmailTO {

    private String authCode;
}
