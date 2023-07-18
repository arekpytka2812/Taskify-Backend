package com.pytka.taskifybackend.core.models;

import com.pytka.taskifybackend.core.abstraction.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "USER_SETTINGS")
public class UserSettingsEntity extends AbstractEntity {

    @PrimaryKeyJoinColumn
    @Column(name = "userID", nullable = false, unique = true)
    private Long userID;

    @Builder.Default
    @ElementCollection
    @Column(name = "taskTypes")
    private List<String> taskTypes = List.of("TO DO", "IN PROGRESS", "DONE");

    @Builder.Default
    @ElementCollection
    @Column(name = "taskPriorities")
    private List<String> taskPriorities = List.of("LOW", "MID", "HIGH", "CRUCIAL");

}
