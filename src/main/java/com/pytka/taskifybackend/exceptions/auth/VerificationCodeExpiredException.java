package com.pytka.taskifybackend.exceptions.auth;

public class VerificationCodeExpiredException extends RuntimeException {

    public VerificationCodeExpiredException(String email){
        super("[Auth] Auth code for email " + email + " has expired.");
    }

    public VerificationCodeExpiredException(String email, Throwable cause){
        super("[Auth] Auth code for email " + email + " has expired.", cause);
    }
}
