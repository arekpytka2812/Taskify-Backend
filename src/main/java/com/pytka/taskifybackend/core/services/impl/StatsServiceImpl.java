package com.pytka.taskifybackend.core.services.impl;

import com.pytka.taskifybackend.core.DTOs.StatsDTO;
import com.pytka.taskifybackend.core.exceptions.core.DataCouldNotBeSavedException;
import com.pytka.taskifybackend.core.exceptions.core.DataNotFoundException;
import com.pytka.taskifybackend.core.mappers.StatsMapper;
import com.pytka.taskifybackend.core.models.StatsEntity;
import com.pytka.taskifybackend.core.repositories.StatsRepository;
import com.pytka.taskifybackend.core.services.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {


    private final StatsRepository statsRepository;

    private final StatsMapper statsMapper;

    @Override
    public StatsDTO getUserStats(Long userID){

        StatsEntity stats = this.statsRepository.getUserStats(userID)
                .orElseThrow(
                        () -> new DataNotFoundException(StatsEntity.class, userID)
                );

        return this.statsMapper.mapToDTO(stats);
    }

    @Override
    public boolean addUserStats(Long userID){

        StatsEntity stats = StatsEntity.builder()
                .userID(userID)
                .workspacesCreated(0L)
                .tasksCreated(0L)
                .updateInfosCreated(0L)
                .build();

        try{
            this.statsRepository.save(stats);
        }
        catch (DataAccessException e){
            throw new DataCouldNotBeSavedException(StatsEntity.class, e);
        }

        return true;
    }
}
