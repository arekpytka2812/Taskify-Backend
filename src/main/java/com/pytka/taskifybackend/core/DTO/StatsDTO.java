package com.pytka.taskifybackend.core.DTO;


import com.pytka.taskifybackend.core.abstraction.AbstractDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class StatsDTO extends AbstractDTO {

    private Long workspacesCreated;

    private Long tasksCreated;

    private Long updateInfosCreated;

    private Long tasksDeleted;

    private Long finishedOnTimeTasks;

    private Long finishedWithDelayTasks;
}
