package com.pytka.taskifybackend.core.exceptions.auth;


public class TooWeakPasswordException extends RuntimeException{

    public TooWeakPasswordException(){
        super("[AUTH] Provided password does not meet password standards");
    }
    public TooWeakPasswordException(Throwable cause){
        super("[AUTH] Provided password does not meet password standards", cause);
    }
}
