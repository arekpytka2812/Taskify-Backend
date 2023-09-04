package com.pytka.taskifybackend.core.DTOs;


import com.pytka.taskifybackend.core.abstraction.AbstractDTO;
import jakarta.persistence.Column;
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
}
