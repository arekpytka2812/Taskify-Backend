package com.pytka.taskifybackend.core.models;

import com.pytka.taskifybackend.core.abstraction.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @JoinColumn(name = "CR_USER", referencedColumnName = "ID")
    @Column(name = "userID")
    private Long userID;

    @Column(name = "priority")
    private String priority;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "UPDATE_INFO", referencedColumnName = "ID")
    @Column(name = "taskUpdates")
    private List<UpdateInfoEntity> taskUpdates;

    @Column(name = "expirationDate", nullable = true)
    private LocalDateTime expirationDate;

}



