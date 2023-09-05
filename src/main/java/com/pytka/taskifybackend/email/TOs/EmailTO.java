package com.pytka.taskifybackend.email.TOs;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@RequiredArgsConstructor
@SuperBuilder
@Getter
@Setter
public class EmailTO {

    private String email;

    private String username;

    private String subject;

    private String body;

}
