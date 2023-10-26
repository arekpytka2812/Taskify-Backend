package com.pytka.taskifybackend.auth.service;

import com.pytka.taskifybackend.auth.TO.*;

public interface AuthService {

    void generateRegisterCode(AuthCodeRequest request);

    void regenerateRegisterCode(AuthCodeRequest request);

    AuthResponse register(RegisterRequest request);

    AuthResponse login(AuthenticationRequest request);

    AuthResponse changePassword(ChangePasswordRequest request);

    void remindPassword(RemindPasswordRequest request);

    AuthResponse setNewPasswordAfterReset(ForgottenPasswordRequest request);

}
