package com.pytka.taskifybackend.scheduling.service;

public interface NotificationScheduler {

    void checkAndSendTaskExpirationNotification();
}
