package com.pytka.taskifybackend.scheduling.service.impl;

import com.pytka.taskifybackend.email.TOs.EmailTO;
import com.pytka.taskifybackend.email.TOs.NotificationEmailTO;
import com.pytka.taskifybackend.email.service.EmailProducer;
import com.pytka.taskifybackend.scheduling.service.EmailScheduler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:scheduling.properties")
public class EmailSchedulerImpl implements EmailScheduler {

    private final EmailProducer emailProducer;

    private final ThreadPoolTaskScheduler taskScheduler;

    public EmailSchedulerImpl(
            EmailProducer emailProducer,
            @Qualifier("emailTaskScheduler") ThreadPoolTaskScheduler taskScheduler
    ) {
        this.emailProducer = emailProducer;
        this.taskScheduler = taskScheduler;
    }

    @Override
    @Scheduled(cron = "${scheduling.email.notification.cron}")
    public void sendNotificationEmails() {
        EmailTO email = NotificationEmailTO.builder()
                .email("262753@student.pwr.edu.pl")
                .subject("Scheduler Test Email!")
                .body("This is scheduled email.\nPS plz work")
                .type("notification")
                .build();

        this.taskScheduler.submit(
                () -> this.emailProducer.sendEmail(email)
        );
    }
}
