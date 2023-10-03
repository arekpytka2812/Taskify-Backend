package com.pytka.taskifybackend.email.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pytka.taskifybackend.email.TOs.AuthEmailTO;
import com.pytka.taskifybackend.email.TOs.EmailTO;
import com.pytka.taskifybackend.email.TOs.NotificationEmailTO;
import com.pytka.taskifybackend.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:email.properties")
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    private final ObjectMapper objectMapper;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    @RabbitListener(queues = "email-queue")
    public void processEmail(String emailMessage) {

        try {
            EmailTO emailBase = objectMapper.readValue(emailMessage, EmailTO.class);

            String emailType = emailBase.getType();

            if(emailType.equals("auth")){
                AuthEmailTO authEmailTO = objectMapper.readValue(emailMessage, AuthEmailTO.class);
                sendEmailForAuthentication(authEmailTO);
            }
            else if(emailType.equals("notification")){
                NotificationEmailTO notificationEmailTO
                        = objectMapper.readValue(emailMessage, NotificationEmailTO.class);
                sendEmailAsNotification(notificationEmailTO);
            }
            else{
                sendEmail(emailBase);
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Async
    protected void sendEmail(EmailTO email){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(email.getEmail());
        message.setSubject(email.getSubject());
        message.setText(email.getBody());

        mailSender.send(message);
    }


    @Async
    protected void sendEmailForAuthentication(AuthEmailTO authEmailTO) {

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

    @Async
    protected void sendEmailAsNotification(NotificationEmailTO notificationEmailTO) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(notificationEmailTO.getEmail());
        message.setSubject(notificationEmailTO.getSubject());
        message.setText(notificationEmailTO.getBody());

        mailSender.send(message);
    }
}
