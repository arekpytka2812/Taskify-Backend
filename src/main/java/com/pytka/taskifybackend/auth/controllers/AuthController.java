package com.pytka.taskifybackend.auth.controllers;

import com.pytka.taskifybackend.auth.tos.AuthResponse;
import com.pytka.taskifybackend.auth.services.impl.AuthService;
import com.pytka.taskifybackend.auth.tos.AuthenticationRequest;
import com.pytka.taskifybackend.auth.tos.RegisterRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(this.authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(this.authService.login(request));
    }
}
