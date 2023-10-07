package com.pytka.taskifybackend.core.DTO;

import com.pytka.taskifybackend.core.abstraction.AbstractDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class TaskDTO extends AbstractDTO {

    private String name;

    private String description;

    private String taskType;

    private String priority;

    private Boolean notifications;

    private Long workspaceID;

    private List<UpdateInfoDTO> taskUpdates;

    private LocalDateTime createDate;

    private LocalDateTime expirationDate;
}
