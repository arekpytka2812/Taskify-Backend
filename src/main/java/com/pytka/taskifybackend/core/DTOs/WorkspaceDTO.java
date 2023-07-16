package com.pytka.taskifybackend.core.DTOs;

import com.pytka.taskifybackend.core.abstraction.AbstractDTO;
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
public class WorkspaceDTO extends AbstractDTO {

    private String name;

    private List<TaskDTO> tasks;

}
