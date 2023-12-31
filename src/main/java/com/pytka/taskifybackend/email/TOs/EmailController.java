package com.pytka.taskifybackend.email.TOs;

import com.pytka.taskifybackend.email.service.EmailProducer;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailProducer emailProducer;

    @PostMapping("/send")
    @RolesAllowed("USER")
    public ResponseEntity<String> sendEmail(
            @RequestBody EmailTO email
    ) {
        this.emailProducer.sendEmail(email);

        return ResponseEntity.ok("Yipppe");
    }

}
