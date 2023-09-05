package com.pytka.taskifybackend.exceptions.auth;

public class AuthCodeExpiredException extends RuntimeException {

    public AuthCodeExpiredException(String email){
        super("[Auth] Auth code for email " + email + " has expired.");
    }

    public AuthCodeExpiredException(String email, Throwable cause){
        super("[Auth] Auth code for email " + email + " has expired.", cause);
    }
}
