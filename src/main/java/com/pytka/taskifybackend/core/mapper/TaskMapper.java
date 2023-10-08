package com.pytka.taskifybackend.core.mapper;

import com.pytka.taskifybackend.core.DTO.TaskDTO;
import com.pytka.taskifybackend.core.abstraction.AbstractMapper;
import com.pytka.taskifybackend.core.model.TaskEntity;
import org.mapstruct.Mapper;

@Mapper
public interface TaskMapper extends AbstractMapper<TaskDTO, TaskEntity> {

}
