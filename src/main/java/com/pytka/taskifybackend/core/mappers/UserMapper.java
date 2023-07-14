package com.pytka.taskifybackend.core.mappers;

import com.pytka.taskifybackend.core.DTOs.UserDTO;
import com.pytka.taskifybackend.core.abstraction.AbstractMapper;
import com.pytka.taskifybackend.core.models.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper extends AbstractMapper<UserDTO, UserEntity> {

}
