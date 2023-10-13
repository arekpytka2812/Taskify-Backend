package com.pytka.taskifybackend.scheduling.service.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pytka.taskifybackend.core.model.TaskNotification;
import com.pytka.taskifybackend.core.service.TaskService;
import com.pytka.taskifybackend.scheduling.dto.TaskNotificationDTO;
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
    @Scheduled(fixedRate = 60000)
    public void checkAndSendTaskExpirationNotification() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime taskTime = LocalDateTime.parse(
                LocalDateTime.now().plusHours(1).format(formatter)
        );

        List<TaskNotification> notifications = this.taskService.getTasksExpiringIn(taskTime);

        for(TaskNotification taskNotification : notifications){
            String userChannel = "/user/" + taskNotification.getUserID() + "/notification";
            String notificationMessage = "Your task: " + taskNotification.getTaskName()
                    + " in workspace: " + taskNotification.getWorkspaceName()
                    + " will expire in 1 hour!";

            TaskNotificationDTO dto = TaskNotificationDTO.builder()
                            .message(notificationMessage)
                            .build();

            System.out.println("\n[DEBUG - NOTIFICATION] At channel: " + userChannel + " sent notification : \n" + taskNotification.toString() + "\n");

            messagingTemplate.convertAndSend(userChannel, dto);
        }

        System.out.println("\n\n[DEBUG - NOTIFICATION] " + taskTime + " Sent user notifications!\n\n");
    }
}
