package com.pytka.taskifybackend.core.mapper;

import com.pytka.taskifybackend.core.DTO.UpdateInfoDTO;
import com.pytka.taskifybackend.core.abstraction.AbstractMapper;
import com.pytka.taskifybackend.core.model.UpdateInfoEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UpdateInfoMapper extends AbstractMapper<UpdateInfoDTO, UpdateInfoEntity> {
}
