package com.pytka.taskifybackend.auth.services.impl;

import com.pytka.taskifybackend.auth.models.UserRegisterCodeEntity;
import com.pytka.taskifybackend.auth.repositories.UserRegisterCodeRepository;
import com.pytka.taskifybackend.auth.tos.*;
import com.pytka.taskifybackend.auth.utils.AuthCodeGenerator;
import com.pytka.taskifybackend.config.security.JwtService;
import com.pytka.taskifybackend.email.TOs.AuthEmailTO;
import com.pytka.taskifybackend.email.service.EmailService;
import com.pytka.taskifybackend.exceptions.auth.*;
import com.pytka.taskifybackend.exceptions.core.DataCouldNotBeSavedException;
import com.pytka.taskifybackend.exceptions.core.DataNotFoundException;
import com.pytka.taskifybackend.exceptions.core.UserNotFoundException;
import com.pytka.taskifybackend.core.models.Role;
import com.pytka.taskifybackend.core.models.UserEntity;
import com.pytka.taskifybackend.core.repositories.UserRepository;
import com.pytka.taskifybackend.core.services.StatsService;
import com.pytka.taskifybackend.core.services.UserSettingsService;
import com.pytka.taskifybackend.core.services.WorkspaceService;
import com.pytka.taskifybackend.core.utils.PasswordChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final UserRegisterCodeRepository userRegisterCodeRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserSettingsService userSettingsService;

    private final WorkspaceService workspaceService;

    private final StatsService statsService;

    private final EmailService emailService;

    private final AuthCodeGenerator codeGenerator;

    public void generateRegisterCode(AuthCodeRequest request){

        String email = request.getEmail();

        if(this.userRepository.existsByEmail(email)){
            throw new EmailAlreadyExistsException(email, new DataIntegrityViolationException(""));
        }

        String authCode = codeGenerator.generateCode(email);

        UserRegisterCodeEntity userRegisterCodeEntity = UserRegisterCodeEntity.builder()
                .email(email)
                .code(authCode)
                .expirationDate(LocalDateTime.now().plusMinutes(3))
                .build();

        try{
            userRegisterCodeRepository.save(userRegisterCodeEntity);
        }
        catch (DataAccessException e) {
            throw new DataCouldNotBeSavedException(UserRegisterCodeEntity.class, request.getEmail(), e);
        }

        sendRegisterEmail(email, request.getUsername(), authCode);
    }

    private void sendRegisterEmail(String email, String username, String authCode){

        AuthEmailTO authEmail = AuthEmailTO.builder()
                .email(email)
                .username(username)
                .authCode(authCode)
                .subject("Your Authentication Code!")
                .build();

        this.emailService.sendEmailForAuthentication(authEmail);
    }

    public void regenerateRegisterCode(AuthCodeRequest request){

        String email = request.getEmail();

        UserRegisterCodeEntity entity = this.userRegisterCodeRepository.findByEmail(email)
                .orElseThrow(() ->
                        new EmailNotFoundException(email)
                );

        String newCode = codeGenerator.generateCode(email);

        entity.setCode(newCode);
        entity.setExpirationDate(LocalDateTime.now().plusMinutes(3));

        this.userRegisterCodeRepository.save(entity);

        sendRegisterEmail(email, request.getUsername(), newCode);
    }



    public AuthResponse register(RegisterRequest request){

        AuthCodeRequest codeRequest = AuthCodeRequest.builder()
                .email(request.getEmail())
                .authCode(request.getAuthCode())
                .sentRequestDate(request.getSentDate())
                .build();

        checkRegisterCode(codeRequest);

        String password = request.getPassword();

        if(!PasswordChecker.isValidPassword(password)){
            throw new TooWeakPasswordException();
        }

        String encodedPassword = passwordEncoder.encode(password);

        UserEntity userEntity = UserEntity.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
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

    private void checkRegisterCode(AuthCodeRequest request){

        UserRegisterCodeEntity userRegisterCodeEntity =
                userRegisterCodeRepository.findByEmail(request.getEmail())
                        .orElseThrow(() ->
                                new DataNotFoundException(UserRegisterCodeEntity.class)
                        );

        if(!userRegisterCodeEntity.getCode().equals(request.getAuthCode())){
            throw new AuthCodeDoesNotMatchException(request.getEmail());
        }

        if(userRegisterCodeEntity.getExpirationDate().isBefore(request.getSentRequestDate())){
            throw new AuthCodeExpiredException(request.getEmail());
        }

        this.userRegisterCodeRepository.delete(userRegisterCodeEntity);
    }

    private void createRecordsWhileRegistering(Long userID){
        this.statsService.addUserStats(userID);
        this.userSettingsService.createUserSettingsRecordByUser(userID);
        this.workspaceService.addWorkspace(userID);
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
