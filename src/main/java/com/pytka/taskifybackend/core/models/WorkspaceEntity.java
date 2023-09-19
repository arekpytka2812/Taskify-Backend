package com.pytka.taskifybackend.core.models;

import com.pytka.taskifybackend.core.abstraction.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Data
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "WORKSPACES")
public class WorkspaceEntity extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "TASKS", referencedColumnName = "ID")
    @Column(name = "tasks")
    private List<TaskEntity> tasks;

    @JoinColumn(name = "CR_USER", referencedColumnName = "ID")
    @Column(name = "userID")
    private Long userID;

}
