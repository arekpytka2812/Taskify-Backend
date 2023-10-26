package com.pytka.taskifybackend.core.mapper;

import com.pytka.taskifybackend.core.DTO.WorkspaceLiteDTO;
import com.pytka.taskifybackend.core.abstraction.AbstractMapper;
import com.pytka.taskifybackend.core.model.WorkspaceEntity;
import org.mapstruct.Mapper;

@Mapper
public interface WorkspaceLiteMapper  extends AbstractMapper<WorkspaceLiteDTO, WorkspaceEntity> {
}
