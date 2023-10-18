package com.pytka.taskifybackend.core.mapper;

import com.pytka.taskifybackend.core.DTO.UserSettingsDTO;
import com.pytka.taskifybackend.core.abstraction.AbstractMapper;
import com.pytka.taskifybackend.core.model.UserSettingsEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserSettingsMapper extends AbstractMapper<UserSettingsDTO, UserSettingsEntity> {
}
