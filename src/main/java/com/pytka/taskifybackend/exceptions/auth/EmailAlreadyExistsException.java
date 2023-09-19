package com.pytka.taskifybackend.exceptions.auth;

public class EmailAlreadyExistsException extends RuntimeException{

    public EmailAlreadyExistsException(String email, Throwable cause){
        super("[AUTH] Email " + email + " already used.", cause);
    }
}
