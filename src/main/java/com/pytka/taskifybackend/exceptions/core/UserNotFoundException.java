package com.pytka.taskifybackend.exceptions.core;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(){
        super("[USER] User not found.");
    }
    public UserNotFoundException(long id){
        super("[USER] User with " + id + " not found.");
    }
}
