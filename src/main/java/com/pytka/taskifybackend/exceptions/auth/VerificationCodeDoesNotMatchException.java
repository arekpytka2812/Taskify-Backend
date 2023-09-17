package com.pytka.taskifybackend.exceptions.auth;

public class VerificationCodeDoesNotMatchException extends RuntimeException{

    public VerificationCodeDoesNotMatchException(String email){
        super("[AUTH] Provided auth code for email " + email + " does not match the generated one.");
    }

    public VerificationCodeDoesNotMatchException(String email, Throwable cause){
        super("[AUTH] Provided auth code for email " + email + " does not match the generated one.", cause);
    }
}
