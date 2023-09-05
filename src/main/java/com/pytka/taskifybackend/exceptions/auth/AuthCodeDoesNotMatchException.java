package com.pytka.taskifybackend.exceptions.auth;

public class AuthCodeDoesNotMatchException extends RuntimeException{

    public AuthCodeDoesNotMatchException(String email){
        super("[AUTH] Provided auth code for email " + email + " does not match the generated one.");
    }

    public AuthCodeDoesNotMatchException(String email, Throwable cause){
        super("[AUTH] Provided auth code for email " + email + " does not match the generated one.", cause);
    }
}
