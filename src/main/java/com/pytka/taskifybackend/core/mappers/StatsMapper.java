package com.pytka.taskifybackend.core.mappers;

import com.pytka.taskifybackend.core.DTOs.StatsDTO;
import com.pytka.taskifybackend.core.abstraction.AbstractMapper;
import com.pytka.taskifybackend.core.models.StatsEntity;
import jakarta.persistence.MapKeyEnumerated;
import org.mapstruct.Mapper;

@Mapper
public interface StatsMapper extends AbstractMapper<StatsDTO, StatsEntity> {
}
