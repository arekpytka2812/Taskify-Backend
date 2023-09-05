package com.pytka.taskifybackend.email.service.impl;


import com.pytka.taskifybackend.email.TOs.AuthEmailTO;
import com.pytka.taskifybackend.email.TOs.EmailTO;
import com.pytka.taskifybackend.email.TOs.NotificationEmailTO;
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
        message.setTo(email.getEmail());
        message.setSubject(email.getSubject());
        message.setText(email.getBody());

        mailSender.send(message);
    }

    @Override
    public void sendEmailForAuthentication(AuthEmailTO authEmailTO) {

         String authCodeEmailBody =
                "Hello " + authEmailTO.getUsername() + "\n\n"
                + "This is you authentication code:\n"
                + authEmailTO.getAuthCode() + "\n\n"
                + "Copy it and paste into text field in app!\n"
                + "Wishing You happy using of Taskify!";

         SimpleMailMessage message = new SimpleMailMessage();

         message.setTo(authEmailTO.getEmail());
         message.setSubject("Your Authorization Code!");
         message.setText(authCodeEmailBody);

         mailSender.send(message);
    }

    @Override
    public void sendEmailAsNotification(NotificationEmailTO notificationEmailTO) {

    }
}
