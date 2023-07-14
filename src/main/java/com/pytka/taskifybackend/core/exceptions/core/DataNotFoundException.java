package com.pytka.taskifybackend.core.exceptions.core;

public class DataNotFoundException extends RuntimeException{

    public DataNotFoundException(long id){
        super("[DATA] Data with id: " + id + " not found.");
    }
}
