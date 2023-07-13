package com.pytka.taskifybackend.core.exceptions.auth;


public class TooWeakPasswordException extends RuntimeException{
    public TooWeakPasswordException(Throwable cause){
        super("[AUTH] Provided password does not meet password standards", cause);
    }
}
