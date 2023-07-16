package com.pytka.taskifybackend.core.mappers;

import com.pytka.taskifybackend.core.DTOs.WorkspaceDTO;
import com.pytka.taskifybackend.core.abstraction.AbstractMapper;
import com.pytka.taskifybackend.core.models.WorkspaceEntity;
import org.mapstruct.Mapper;

@Mapper
public interface WorkspaceMapper extends AbstractMapper<WorkspaceDTO, WorkspaceEntity> {
}
