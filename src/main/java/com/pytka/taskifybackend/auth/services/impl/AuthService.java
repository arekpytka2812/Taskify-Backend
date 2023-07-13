package com.pytka.taskifybackend.auth.services.impl;

import com.pytka.taskifybackend.auth.tos.AuthResponse;
import com.pytka.taskifybackend.auth.tos.AuthenticationRequest;
import com.pytka.taskifybackend.auth.tos.RegisterRequest;
import com.pytka.taskifybackend.core.models.Role;
import com.pytka.taskifybackend.core.models.UserEntity;
import com.pytka.taskifybackend.core.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.Token;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

//    @Autowired
//    private final JwtService

    public AuthResponse register(RegisterRequest request){

        String email = request.getEmail();

        if(this.userRepository.existsByEmail(email)){
            // TODO: throw exception
            return null;
        }

        String password = request.getPassword();

//        if(PasswordChecker.isInValidPassword(password)){
//            TODO: throw exception
//        }

        String encodedPassword = passwordEncoder.encode(password);

        UserEntity userEntity = UserEntity.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(email)
                .username(request.getUsername())
                .password(encodedPassword)
                .role(Role.USER)
                .build();

        this.userRepository.save(userEntity);

        // TODO: create user settings record after implementing logic

//        String jwtToken = jwtSer

        return new AuthResponse();
    }

    public AuthResponse login(AuthenticationRequest request){
        return null;
    }
}
