package com.pytka.taskifybackend.core.mapper;

import com.pytka.taskifybackend.core.DTO.StatsDTO;
import com.pytka.taskifybackend.core.abstraction.AbstractMapper;
import com.pytka.taskifybackend.core.model.StatsEntity;
import org.mapstruct.Mapper;

@Mapper
public interface StatsMapper extends AbstractMapper<StatsDTO, StatsEntity> {
}
