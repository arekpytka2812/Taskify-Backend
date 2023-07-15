package com.pytka.taskifybackend.core.mappers;

import com.pytka.taskifybackend.core.DTOs.UpdateInfoDTO;
import com.pytka.taskifybackend.core.abstraction.AbstractMapper;
import com.pytka.taskifybackend.core.models.UpdateInfoEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UpdateInfoMapper extends AbstractMapper<UpdateInfoDTO, UpdateInfoEntity> {
}
