package com.pytka.taskifybackend.exceptions.core;

public class DataCouldNotBeDeletedException extends RuntimeException{

    public <T> DataCouldNotBeDeletedException(Class<T> clazz, long id){
        super("[DATA] Data of type: " + clazz.getSimpleName()
                + " with id: " + id + " could not be deleted.");
    }
}
