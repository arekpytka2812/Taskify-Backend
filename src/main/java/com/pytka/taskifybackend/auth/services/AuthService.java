package com.pytka.taskifybackend.auth.services;

import com.pytka.taskifybackend.auth.tos.*;

public interface AuthService {

    void generateRegisterCode(AuthCodeRequest request);

    void regenerateRegisterCode(AuthCodeRequest request);

    AuthResponse register(RegisterRequest request);

    AuthResponse login(AuthenticationRequest request);

    AuthResponse changePassword(ChangePasswordRequest request);

    void remindPassword(RemindPasswordRequest request);

    AuthResponse setNewPasswordAfterReset(ForgottenPasswordRequest request);

}
