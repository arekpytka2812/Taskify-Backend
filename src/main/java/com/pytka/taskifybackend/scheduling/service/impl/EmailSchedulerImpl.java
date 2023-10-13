package com.pytka.taskifybackend.scheduling.service.impl;

import com.pytka.taskifybackend.core.model.TaskAppNotification;
import com.pytka.taskifybackend.core.model.TaskEmailNotification;
import com.pytka.taskifybackend.core.service.TaskService;
import com.pytka.taskifybackend.email.TOs.EmailTO;
import com.pytka.taskifybackend.email.TOs.NotificationEmailTO;
import com.pytka.taskifybackend.email.service.EmailProducer;
import com.pytka.taskifybackend.scheduling.service.EmailScheduler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:scheduling.properties")
public class EmailSchedulerImpl implements EmailScheduler {

    private final EmailProducer emailProducer;

    private final ThreadPoolTaskScheduler taskScheduler;

    private final TaskService taskService;

    public EmailSchedulerImpl(
            EmailProducer emailProducer,
            @Qualifier("emailTaskScheduler") ThreadPoolTaskScheduler taskScheduler,
            TaskService taskService
    ) {
        this.emailProducer = emailProducer;
        this.taskScheduler = taskScheduler;
        this.taskService = taskService;
    }

    @Override
    @Scheduled(cron = "${scheduling.email.notification.cron}")
    public void sendNotificationEmails() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expDate = LocalDateTime.now().plusDays(1);

        List<TaskEmailNotification> tasks = this.taskService.getTasksForEmailNotifications(
                now,
                expDate
        );

        Map<Long, List<TaskEmailNotification>> groupedTasks = tasks.stream()
                .collect(Collectors.groupingBy(TaskEmailNotification::getUserID));

        for(Long userID : groupedTasks.keySet()){

            createAndSendEmail(userID, groupedTasks);
        }

    }

    private void createAndSendEmail(
            Long userID,
            Map<Long, List<TaskEmailNotification>> map
    ) {

        String username = map.get(userID).get(0).getUsername();
        String userEmail = map.get(userID).get(0).getUserEmail();
        String greeting = username == null ? "Hello!\n\n" : "Hello " + username + "!\n\n";

        StringBuilder body = new StringBuilder(greeting);

        for(TaskEmailNotification task : map.get(userID)){

            String expDateTrunked = task.getExpirationDate().getHour() + ":" + task.getExpirationDate().getMinute();

            body.append("Your task: ").append(task.getTaskName())
                    .append(" in workspace: ").append(task.getWorkspaceName())
                    .append(" will expire today at: ").append(expDateTrunked).append("\n");

        }

        EmailTO email = NotificationEmailTO.builder()
                .email(userEmail)
                .subject("Expiring tasks!")
                .body(body.toString())
                .type("notification")
                .build();

        this.taskScheduler.submit(
                () -> this.emailProducer.sendEmail(email)
        );

    }
}
