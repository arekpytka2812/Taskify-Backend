package com.pytka.taskifybackend.exceptions.core;


public class DataCouldNotBeSavedException extends RuntimeException{

    public <T> DataCouldNotBeSavedException(Class<T> clazz, Throwable cause){
        super("[DATA] Data of type: " + clazz.getSimpleName() + ", could not be saved.", cause);
    }

    public <T> DataCouldNotBeSavedException(Class<T> clazz, String name, Throwable cause){
        super("[DATA] Data of type: " + clazz.getSimpleName() + ", entity name: " + name + " could not be saved.", cause);
    }
}
