package com.pytka.taskifybackend.core.mapper;

import com.pytka.taskifybackend.core.DTO.UserDTO;
import com.pytka.taskifybackend.core.abstraction.AbstractMapper;
import com.pytka.taskifybackend.core.model.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper extends AbstractMapper<UserDTO, UserEntity> {

}
