package com.pytka.taskifybackend.auth.service.impl;

import com.pytka.taskifybackend.auth.model.UserVerificationCodeEntity;
import com.pytka.taskifybackend.auth.repository.UserVerificationCodeRepository;
import com.pytka.taskifybackend.auth.service.AuthService;
import com.pytka.taskifybackend.auth.TO.*;
import com.pytka.taskifybackend.auth.utils.AuthCodeGenerator;
import com.pytka.taskifybackend.config.security.JwtService;
import com.pytka.taskifybackend.email.TOs.AuthEmailTO;
import com.pytka.taskifybackend.email.service.EmailProducer;
import com.pytka.taskifybackend.exceptions.auth.*;
import com.pytka.taskifybackend.exceptions.core.DataCouldNotBeSavedException;
import com.pytka.taskifybackend.exceptions.core.DataNotFoundException;
import com.pytka.taskifybackend.exceptions.core.UserNotFoundException;
import com.pytka.taskifybackend.core.model.Role;
import com.pytka.taskifybackend.core.model.UserEntity;
import com.pytka.taskifybackend.core.repository.UserRepository;
import com.pytka.taskifybackend.core.service.StatsService;
import com.pytka.taskifybackend.core.service.UserSettingsService;
import com.pytka.taskifybackend.core.service.WorkspaceService;
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
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final UserVerificationCodeRepository userVerificationCodeRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserSettingsService userSettingsService;

    private final WorkspaceService workspaceService;

    private final StatsService statsService;

    private final EmailProducer emailProducer;

    private final AuthCodeGenerator codeGenerator;

    public void generateRegisterCode(AuthCodeRequest request){

        String email = request.getEmail();

        if(this.userRepository.existsByEmail(email)){
            throw new EmailAlreadyExistsException(email, new DataIntegrityViolationException(""));
        }

        String authCode = codeGenerator.generateCode(email);

        UserVerificationCodeEntity userVerificationCodeEntity = UserVerificationCodeEntity.builder()
                .email(email)
                .code(authCode)
                .expirationDate(LocalDateTime.now().plusMinutes(3))
                .build();

        try{
            userVerificationCodeRepository.save(userVerificationCodeEntity);
        }
        catch (DataAccessException e) {
            throw new DataCouldNotBeSavedException(UserVerificationCodeEntity.class, request.getEmail(), e);
        }

        sendRegisterEmail(email, request.getUsername(), authCode);
    }

    private void sendRegisterEmail(String email, String username, String authCode){

        AuthEmailTO authEmail = AuthEmailTO.builder()
                .email(email)
                .username(username)
                .authCode(authCode)
                .subject("Your Authentication Code!")
                .type("auth")
                .build();

        this.emailProducer.sendEmail(authEmail);
    }

    public void regenerateRegisterCode(AuthCodeRequest request){

        String email = request.getEmail();

        UserVerificationCodeEntity entity = this.userVerificationCodeRepository.findByEmail(email)
                .orElseThrow(() ->
                        new EmailNotFoundException(email)
                );

        String newCode = codeGenerator.generateCode(email);

        entity.setCode(newCode);
        entity.setExpirationDate(LocalDateTime.now().plusMinutes(3));
        entity.setUpdateDate(LocalDateTime.now());

        this.userVerificationCodeRepository.save(entity);

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

        UserVerificationCodeEntity userVerificationCodeEntity =
                userVerificationCodeRepository.findByEmail(request.getEmail())
                        .orElseThrow(() ->
                                new DataNotFoundException(UserVerificationCodeEntity.class)
                        );

        if(!userVerificationCodeEntity.getCode().equals(request.getAuthCode())){
            throw new VerificationCodeDoesNotMatchException(request.getEmail());
        }

        if(userVerificationCodeEntity.getExpirationDate().isBefore(request.getSentRequestDate())){
            throw new VerificationCodeExpiredException(request.getEmail());
        }

        if(request.getSentRequestDate().isBefore(userVerificationCodeEntity.getCreateDate())){
            throw new VerificationCodeExpiredException(request.getEmail());
        }

        this.userVerificationCodeRepository.delete(userVerificationCodeEntity);
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
                .ID(user.getID())
                .token(newToken)
                .build();
    }

    @Override
    public void remindPassword(RemindPasswordRequest request){

        UserEntity user = this.userRepository.findByEmail(request.getEmail())
                .orElseThrow(UserNotFoundException::new);

        String verificationCode = codeGenerator.generateCode(request.getEmail());

        UserVerificationCodeEntity entity = UserVerificationCodeEntity.builder()
                .email(request.getEmail())
                .code(verificationCode)
                .expirationDate(LocalDateTime.now().plusMinutes(5))
                .build();

        try{
            this.userVerificationCodeRepository.save(entity);
        }
        catch(DataAccessException e){
            throw new DataCouldNotBeSavedException(UserVerificationCodeEntity.class, e);
        }

        sendRegisterEmail(request.getEmail(), user.getUsername(), verificationCode);
    }

    @Override
    public AuthResponse setNewPasswordAfterReset(ForgottenPasswordRequest request){

        UserEntity user = this.userRepository.findByEmail(request.getEmail())
                .orElseThrow(UserNotFoundException::new);

        UserVerificationCodeEntity verificationCodeEntity = this.userVerificationCodeRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new DataNotFoundException(UserVerificationCodeEntity.class));

        if(!verificationCodeEntity.getCode().equals(request.getVerificationCode())){
            throw new VerificationCodeDoesNotMatchException(request.getEmail());
        }

        if(verificationCodeEntity.getExpirationDate().isBefore(request.getSentDate())){
            throw new VerificationCodeExpiredException(request.getEmail());
        }

        if(request.getSentDate().isBefore(verificationCodeEntity.getCreateDate())){
            throw new VerificationCodeExpiredException(request.getEmail());
        }

        this.userVerificationCodeRepository.delete(verificationCodeEntity);

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        user.setPassword(encodedPassword);

        try{
            userRepository.save(user);
        }
        catch (DataAccessException e){
            throw new DataCouldNotBeSavedException(UserEntity.class, e);
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String newToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .ID(user.getID())
                .token(newToken)
                .build();

    }
}
