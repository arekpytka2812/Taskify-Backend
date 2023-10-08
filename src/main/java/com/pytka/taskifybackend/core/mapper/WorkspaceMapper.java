package com.pytka.taskifybackend.core.mapper;

import com.pytka.taskifybackend.core.DTO.WorkspaceDTO;
import com.pytka.taskifybackend.core.abstraction.AbstractMapper;
import com.pytka.taskifybackend.core.model.WorkspaceEntity;
import org.mapstruct.Mapper;

@Mapper
public interface WorkspaceMapper extends AbstractMapper<WorkspaceDTO, WorkspaceEntity> {
}
