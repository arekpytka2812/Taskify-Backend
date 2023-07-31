package com.pytka.taskifybackend.core.mappers;

import com.pytka.taskifybackend.core.DTOs.WorkspaceLiteDTO;
import com.pytka.taskifybackend.core.abstraction.AbstractMapper;
import com.pytka.taskifybackend.core.models.WorkspaceEntity;
import org.mapstruct.Mapper;

@Mapper
public interface WorkspaceLiteMapper  extends AbstractMapper<WorkspaceLiteDTO, WorkspaceEntity> {
}
