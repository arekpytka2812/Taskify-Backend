package com.pytka.taskifybackend.core.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private Boolean emailNotifications;

    private Boolean appNotifications;

    private Long workspaceID;

    private List<UpdateInfoDTO> taskUpdates;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime expirationDate;
}
