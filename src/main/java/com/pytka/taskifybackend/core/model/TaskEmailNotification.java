package com.pytka.taskifybackend.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
public class TaskEmailNotification {

    private Long userID;

    private String userEmail;

    private String username;

    private String workspaceName;

    private String taskName;

    private LocalDateTime expirationDate;

    public TaskEmailNotification(
            Long userID,
            String userEmail,
            String username,
            String workspaceName,
            String taskName,
            LocalDateTime expirationDate
    ) {
        this.userID = userID;
        this.userEmail = userEmail;
        this.username = username;
        this.workspaceName = workspaceName;
        this.taskName = taskName;
        this.expirationDate = expirationDate;
    }

}
