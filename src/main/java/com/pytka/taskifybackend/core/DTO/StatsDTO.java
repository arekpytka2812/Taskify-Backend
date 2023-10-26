package com.pytka.taskifybackend.core.DTO;


import com.pytka.taskifybackend.core.abstraction.AbstractDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class StatsDTO extends AbstractDTO {

    private Long workspacesCreated;

    private Long workspacesDeleted;

    private Long tasksCreated;

    private Long tasksDeleted;

    private Long updateInfosCreated;

    private Long finishedOnTimeTasks;

    private Long finishedWithDelayTasks;
}
