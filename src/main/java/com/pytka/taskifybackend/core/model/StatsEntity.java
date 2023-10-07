package com.pytka.taskifybackend.core.model;

import com.pytka.taskifybackend.core.abstraction.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "STATS")
public class StatsEntity extends AbstractEntity {

    @PrimaryKeyJoinColumn
    @Column(name = "userID", nullable = false, unique = true)
    private Long userID;

    @Column(name = "workspacesCreated")
    private Long workspacesCreated;

    @Column(name = "workspacesDeleted")
    private Long workspacesDeleted;

    @Column(name = "tasksCreated")
    private Long tasksCreated;

    @Column(name = "tasksDeleted")
    private Long tasksDeleted;

    @Column(name = "updateInfosCreated")
    private Long updateInfosCreated;

    @Column(name = "finishedOnTimeTasks")
    private Long finishedOnTimeTasks;

    @Column(name = "finishedWithDelayTasks")
    private Long finishedWithDelayTasks;

}
