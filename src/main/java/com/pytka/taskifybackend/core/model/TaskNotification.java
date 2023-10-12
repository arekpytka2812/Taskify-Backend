package com.pytka.taskifybackend.core.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class TaskNotification {

    private Long userID;

    private String workspaceName;

    private String taskName;

    public TaskNotification(
            Long userID,
            String workspaceName,
            String taskName
    ) {
        this.userID = userID;
        this.workspaceName = workspaceName;
        this.taskName = taskName;
    }

}
