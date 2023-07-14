package com.pytka.taskifybackend.core.exceptions.handlers;

import com.pytka.taskifybackend.core.exceptions.ApiError;
import com.pytka.taskifybackend.core.exceptions.core.DataCouldNotBeSavedException;
import com.pytka.taskifybackend.core.exceptions.core.DataNotFoundException;
import com.pytka.taskifybackend.core.exceptions.core.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CoreHandler {

    @ExceptionHandler(DataCouldNotBeSavedException.class)
    public ResponseEntity<ApiError> handleDataCouldNotBeSavedException(Exception e, HttpServletRequest request){

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.statusCode()));
    }


    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiError> handleDataNotFoundException(Exception e, HttpServletRequest request){

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.statusCode()));
    }
}
