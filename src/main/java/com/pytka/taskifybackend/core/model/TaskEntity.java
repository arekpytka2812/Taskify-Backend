package com.pytka.taskifybackend.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pytka.taskifybackend.core.abstraction.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TASKS")
public class TaskEntity extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "taskType")
    private String taskType;

    @Column(name = "priority")
    private String priority;

    @Column(name = "emailNotifications")
    private Boolean emailNotifications = false;

    @Column(name = "appNotifications")
    private Boolean appNotifications = false;

    @JoinColumn(name = "WORKSPACES", referencedColumnName = "ID")
    @Column(name = "workspaceID", nullable = false)
    private Long workspaceID;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "UPDATE_INFO", referencedColumnName = "ID")
    @Column(name = "taskUpdates")
    private List<UpdateInfoEntity> taskUpdates;

    @Column(name = "expirationDate")
    private LocalDateTime expirationDate;

}



