package com.pytka.taskifybackend.scheduling.service.impl;

import com.pytka.taskifybackend.auth.repository.UserVerificationCodeRepository;
import com.pytka.taskifybackend.scheduling.service.VerificationCodeCleaner;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class VerificationCodeCleanerImpl implements VerificationCodeCleaner {

    private final UserVerificationCodeRepository verificationCodeRepository;

    @Override
    @Async
    @Scheduled(fixedRate = 60000)
    public void clean(){

        List<Long> idsToDelete = this.verificationCodeRepository.getExpiredVerificationCodeIDS(LocalDateTime.now());

        if (idsToDelete == null) {
            return;
        }

        this.verificationCodeRepository.deleteAllById(idsToDelete);

        System.out.println("[DEBUG] Deleted expired verification codes!");
    }
}
