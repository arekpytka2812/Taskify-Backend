package com.pytka.taskifybackend.auth.services.impl;

import com.pytka.taskifybackend.auth.tos.AuthResponse;
import com.pytka.taskifybackend.auth.tos.AuthenticationRequest;
import com.pytka.taskifybackend.auth.tos.ChangePasswordRequest;
import com.pytka.taskifybackend.auth.tos.RegisterRequest;
import com.pytka.taskifybackend.config.security.JwtService;
import com.pytka.taskifybackend.core.DTOs.WorkspaceDTO;
import com.pytka.taskifybackend.core.exceptions.auth.EmailAlreadyExistsException;
import com.pytka.taskifybackend.core.exceptions.auth.TooWeakPasswordException;
import com.pytka.taskifybackend.core.exceptions.core.DataCouldNotBeSavedException;
import com.pytka.taskifybackend.core.exceptions.core.UserNotFoundException;
import com.pytka.taskifybackend.core.models.Role;
import com.pytka.taskifybackend.core.models.TaskEntity;
import com.pytka.taskifybackend.core.models.UserEntity;
import com.pytka.taskifybackend.core.models.UserSettingsEntity;
import com.pytka.taskifybackend.core.repositories.UserRepository;
import com.pytka.taskifybackend.core.repositories.UserSettingsRepository;
import com.pytka.taskifybackend.core.services.StatsService;
import com.pytka.taskifybackend.core.services.UserSettingsService;
import com.pytka.taskifybackend.core.services.WorkspaceService;
import com.pytka.taskifybackend.core.utils.PasswordChecker;
import lombok.RequiredArgsConstructor;
import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserSettingsService userSettingsService;

    private final WorkspaceService workspaceService;

    private final StatsService statsService;

    public AuthResponse register(RegisterRequest request){

        String email = request.getEmail();

        if(this.userRepository.existsByEmail(email)){
            throw new EmailAlreadyExistsException(email, new DataIntegrityViolationException(""));
        }

        String password = request.getPassword();

        if(!PasswordChecker.isValidPassword(password)){
            throw new TooWeakPasswordException();
        }

        String encodedPassword = passwordEncoder.encode(password);

        UserEntity userEntity = UserEntity.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(email)
                .username(request.getUsername())
                .password(encodedPassword)
                .role(Role.ROLE_USER)
                .build();

        try{
            this.userRepository.save(userEntity);
        }
        catch (DataAccessException e) {
            throw new DataCouldNotBeSavedException(UserEntity.class, userEntity.getEmail(), e);
        }

        this.createRecordsWhileRegistering(userEntity.getID());

        String jwtToken = jwtService.generateToken(userEntity);

        return AuthResponse.builder()
                .ID(userEntity.getID())
                .token(jwtToken)
                .build();
    }

    private void createRecordsWhileRegistering(Long userID){
        this.userSettingsService.createUserSettingsRecordByUser(userID);
        this.workspaceService.addWorkspace(userID);
        this.statsService.addUserStats(userID);
    }

    public AuthResponse login(AuthenticationRequest request){

        UserEntity user = this.userRepository.findByEmail(request.getEmail())
                .orElseThrow(UserNotFoundException::new);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .ID(user.getID())
                .token(token)
                .build();
    }

    public AuthResponse changePassword(ChangePasswordRequest request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getOldPassword()
                )
        );

        if(!PasswordChecker.isValidPassword(request.getNewPassword())){
            throw new TooWeakPasswordException();
        }

        UserEntity user = this.userRepository
                .findByEmail(request.getEmail())
                .orElseThrow();

        String encodedNewPassword = passwordEncoder.encode(request.getNewPassword());

        user.setPassword(encodedNewPassword);

        try{
            this.userRepository.save(user);
        }
        catch (DataAccessException e){
            throw new DataCouldNotBeSavedException(UserEntity.class, user.getEmail(), e);
        }

        String newToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(newToken)
                .build();
    }
}
