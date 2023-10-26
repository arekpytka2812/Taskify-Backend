package com.pytka.taskifybackend.scheduling.service.impl;

import com.pytka.taskifybackend.core.model.TaskAppNotification;
import com.pytka.taskifybackend.core.service.TaskService;
import com.pytka.taskifybackend.scheduling.dto.TaskAppNotificationDTO;
import com.pytka.taskifybackend.scheduling.service.NotificationScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationSchedulerImpl implements NotificationScheduler {

    private final TaskService taskService;

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    @Scheduled(cron = "${scheduling.app.notification.cron}")
    public void checkAndSendTaskExpirationNotification() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime taskTime = LocalDateTime.parse(
                LocalDateTime.now().plusHours(1).format(formatter)
        );

        List<TaskAppNotification> notifications = this.taskService.getTasksForAppNotifications(taskTime);

        for(TaskAppNotification taskAppNotification : notifications){
            String userChannel = "/user/" + taskAppNotification.getUserID() + "/notification";
            String notificationMessage = "Your task: " + taskAppNotification.getTaskName()
                    + " in workspace: " + taskAppNotification.getWorkspaceName()
                    + " will expire in 1 hour!";

            TaskAppNotificationDTO dto = TaskAppNotificationDTO.builder()
                            .message(notificationMessage)
                            .build();

            System.out.println("\n[DEBUG - NOTIFICATION] At channel: " + userChannel + " sent notification : \n" + taskAppNotification.toString() + "\n");

            messagingTemplate.convertAndSend(userChannel, dto);
        }

        System.out.println("\n\n[DEBUG - NOTIFICATION] " + taskTime + " Sent user notifications!\n\n");
    }
}
