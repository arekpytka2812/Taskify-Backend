package com.pytka.taskifybackend.exceptions.handlers;

import com.pytka.taskifybackend.exceptions.ApiError;
import com.pytka.taskifybackend.exceptions.auth.EmailAlreadyExistsException;
import com.pytka.taskifybackend.exceptions.auth.TooWeakPasswordException;
import com.pytka.taskifybackend.exceptions.core.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class AuthHandler {

    @ExceptionHandler(TooWeakPasswordException.class)
    public ResponseEntity<ApiError> handlePasswordException(Exception e, HttpServletRequest request){

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.statusCode()));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleEmailException(Exception e, HttpServletRequest request){

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.statusCode()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(Exception e, HttpServletRequest request){

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.statusCode()));
    }
}
