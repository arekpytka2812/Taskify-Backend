package com.pytka.taskifybackend.email.service.impl;


import com.pytka.taskifybackend.email.AuthEmailTO;
import com.pytka.taskifybackend.email.EmailTO;
import com.pytka.taskifybackend.email.NotificationEmailTO;
import com.pytka.taskifybackend.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:email.properties")
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendEmail(EmailTO email) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(email.getTo());
        message.setSubject(email.getSubject());
        message.setText(email.getBody());

        mailSender.send(message);
    }

    @Override
    public void sendEmailForAuthentication(AuthEmailTO authEmailTO) {

    }

    @Override
    public void sendEmailAsNotification(NotificationEmailTO notificationEmailTO) {

    }
}
