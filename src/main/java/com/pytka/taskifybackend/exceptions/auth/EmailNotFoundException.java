package com.pytka.taskifybackend.exceptions.auth;

public class EmailNotFoundException extends RuntimeException{

    public EmailNotFoundException(String email){
        super("[AUTH] Email " + email + " not found.");
    }

    public EmailNotFoundException(String email, Throwable cause){
        super("[AUTH] Email " + email + " not found.", cause);
    }
}
