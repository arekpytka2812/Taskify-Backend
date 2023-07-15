package com.pytka.taskifybackend.auth.services.impl;

import com.pytka.taskifybackend.auth.tos.AuthResponse;
import com.pytka.taskifybackend.auth.tos.AuthenticationRequest;
import com.pytka.taskifybackend.auth.tos.RegisterRequest;
import com.pytka.taskifybackend.config.security.JwtService;
import com.pytka.taskifybackend.core.exceptions.auth.EmailAlreadyExistsException;
import com.pytka.taskifybackend.core.exceptions.auth.TooWeakPasswordException;
import com.pytka.taskifybackend.core.exceptions.core.DataCouldNotBeSavedException;
import com.pytka.taskifybackend.core.models.Role;
import com.pytka.taskifybackend.core.models.TaskEntity;
import com.pytka.taskifybackend.core.models.UserEntity;
import com.pytka.taskifybackend.core.repositories.UserRepository;
import com.pytka.taskifybackend.core.utils.PasswordChecker;
import lombok.RequiredArgsConstructor;
import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final AuthenticationManager authenticationManager;

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

        // TODO: create user settings record after implementing logic

        String jwtToken = jwtService.generateToken(userEntity);

        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponse login(AuthenticationRequest request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserEntity user = this.userRepository
                                .findByEmail(request.getEmail())
                                .orElseThrow();

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .build();
    }
}
