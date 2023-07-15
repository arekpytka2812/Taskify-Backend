package com.pytka.taskifybackend.core.mappers;

import com.pytka.taskifybackend.core.DTOs.TaskDTO;
import com.pytka.taskifybackend.core.abstraction.AbstractMapper;
import com.pytka.taskifybackend.core.models.TaskEntity;
import org.mapstruct.Mapper;

@Mapper
public interface TaskMapper extends AbstractMapper<TaskDTO, TaskEntity> {

}
