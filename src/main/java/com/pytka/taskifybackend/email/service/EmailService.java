package com.pytka.taskifybackend.email.service;

import com.pytka.taskifybackend.email.TOs.AuthEmailTO;
import com.pytka.taskifybackend.email.TOs.EmailTO;
import com.pytka.taskifybackend.email.TOs.NotificationEmailTO;

public interface EmailService {

    void sendEmail(EmailTO email);

    void sendEmailForAuthentication(AuthEmailTO authEmailTO);

    void sendEmailAsNotification(NotificationEmailTO notificationEmailTO);
}
