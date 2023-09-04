package com.pytka.taskifybackend.email.service;

import com.pytka.taskifybackend.email.AuthEmailTO;
import com.pytka.taskifybackend.email.EmailTO;
import com.pytka.taskifybackend.email.NotificationEmailTO;

public interface EmailService {

    void sendEmail(EmailTO email);

    void sendEmailForAuthentication(AuthEmailTO authEmailTO);

    void sendEmailAsNotification(NotificationEmailTO notificationEmailTO);
}
