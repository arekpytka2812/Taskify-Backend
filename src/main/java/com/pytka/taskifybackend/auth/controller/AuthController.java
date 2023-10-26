package com.pytka.taskifybackend.auth.controller;

import com.pytka.taskifybackend.auth.TO.*;
import com.pytka.taskifybackend.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/generateCode")
    public void generateRegisterCode(
            @RequestBody AuthCodeRequest request
    ) {
        this.authService.generateRegisterCode(request);
    }

    @PostMapping("/regenerateCode")
    public void regenerateCode(
            @RequestBody AuthCodeRequest request
    ) {
        this.authService.regenerateRegisterCode(request);
    }

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

    @PostMapping("/changePassword")
    public ResponseEntity<AuthResponse> changePassword(
            @RequestBody ChangePasswordRequest request
    ) {
        return ResponseEntity.ok(this.authService.changePassword(request));
    }

    @PostMapping("/remindPassword")
    public void remindPassword(
            @RequestBody RemindPasswordRequest request
    ) {
        this.authService.remindPassword(request);
    }

    @PostMapping("/setNewPassword")
    public ResponseEntity<AuthResponse> setNewPassword(
            @RequestBody ForgottenPasswordRequest request
    ) {
        return ResponseEntity.ok(this.authService.setNewPasswordAfterReset(request));
    }
}
