package com.pytka.taskifybackend.core.exceptions.handlers;

import com.pytka.taskifybackend.core.exceptions.ApiError;
import com.pytka.taskifybackend.core.exceptions.auth.EmailAlreadyExistsException;
import com.pytka.taskifybackend.core.exceptions.auth.TooWeakPasswordException;
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
}
