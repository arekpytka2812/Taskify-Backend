package com.pytka.taskifybackend.core.DTOs;

import com.pytka.taskifybackend.core.abstraction.AbstractDTO;
import com.pytka.taskifybackend.core.models.UpdateInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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

    private List<UpdateInfoDTO> taskUpdates;
}
