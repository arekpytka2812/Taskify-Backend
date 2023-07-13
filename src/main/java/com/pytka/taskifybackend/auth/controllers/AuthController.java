package com.pytka.taskifybackend.auth.controllers;

import com.pytka.taskifybackend.auth.tos.AuthResponse;
import com.pytka.taskifybackend.auth.services.impl.AuthService;
import com.pytka.taskifybackend.auth.tos.AuthenticationRequest;
import com.pytka.taskifybackend.auth.tos.RegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    @Autowired
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(RegisterRequest request){
        return ResponseEntity.ok(this.authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(AuthenticationRequest request){
        return ResponseEntity.ok(this.authService.login(request));
    }
}
