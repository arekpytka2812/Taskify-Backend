package com.pytka.taskifybackend.core.service.impl;

import com.pytka.taskifybackend.core.DTO.StatsDTO;
import com.pytka.taskifybackend.exceptions.core.DataCouldNotBeSavedException;
import com.pytka.taskifybackend.exceptions.core.DataNotFoundException;
import com.pytka.taskifybackend.core.mapper.StatsMapper;
import com.pytka.taskifybackend.core.model.StatsEntity;
import com.pytka.taskifybackend.core.repository.StatsRepository;
import com.pytka.taskifybackend.core.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

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
                .workspacesDeleted(0L)
                .tasksCreated(0L)
                .tasksDeleted(0L)
                .updateInfosCreated(0L)
                .finishedOnTimeTasks(0L)
                .finishedWithDelayTasks(0L)
                .build();

        try{
            this.statsRepository.save(stats);
        }
        catch (DataAccessException e){
            throw new DataCouldNotBeSavedException(StatsEntity.class, e);
        }

        return true;
    }

    @Override
    public void updateStats(Long userID, StatsDTO statsDTO){

        StatsEntity entity = this.statsRepository.getUserStats(userID)
                .orElseThrow(() ->
                        new DataNotFoundException(StatsEntity.class)
                );

        entity.setWorkspacesCreated(statsDTO.getWorkspacesCreated());
        entity.setWorkspacesDeleted(statsDTO.getWorkspacesDeleted());
        entity.setTasksCreated(statsDTO.getTasksCreated());
        entity.setTasksDeleted(statsDTO.getTasksDeleted());
        entity.setUpdateInfosCreated(statsDTO.getUpdateInfosCreated());
        entity.setFinishedOnTimeTasks(statsDTO.getFinishedOnTimeTasks());
        entity.setFinishedWithDelayTasks(statsDTO.getFinishedWithDelayTasks());

        this.statsRepository.save(entity);
    }

    @Override
    public void incrementCreatedWorkspaceNumber(Long userID){

        StatsEntity entity = this.statsRepository.getUserStats(userID)
                .orElseThrow(() ->
                        new DataNotFoundException(StatsEntity.class)
                );

        entity.setWorkspacesCreated(entity.getWorkspacesCreated() + 1);

        this.statsRepository.save(entity);
    }

    @Override
    public void incrementDeletedWorkspaceNumber(Long userID, Long count){

        StatsEntity entity = this.statsRepository.getUserStats(userID)
                .orElseThrow(() ->
                        new DataNotFoundException(StatsEntity.class)
                );

        entity.setWorkspacesDeleted(entity.getWorkspacesDeleted() + count);

        this.statsRepository.save(entity);
    }

    @Override
    public void incrementOverallTaskNumber(Long userID){

        StatsEntity entity = this.statsRepository.getUserStats(userID)
                .orElseThrow(() ->
                        new DataNotFoundException(StatsEntity.class)
                );

        entity.setTasksCreated(entity.getTasksCreated() + 1);

        this.statsRepository.save(entity);
    }

    @Override
    public void incrementDeletedTaskNumber(Long userID, Long count){

        StatsEntity entity = this.statsRepository.getUserStats(userID)
                .orElseThrow(() ->
                        new DataNotFoundException(StatsEntity.class)
                );

        entity.setTasksDeleted(entity.getTasksDeleted() + count);

        this.statsRepository.save(entity);
    }

    @Override
    public void incrementCreatedUpdateInfos(Long userID){

        StatsEntity entity = this.statsRepository.getUserStats(userID)
                .orElseThrow(() ->
                        new DataNotFoundException(StatsEntity.class)
                );

        entity.setUpdateInfosCreated(entity.getUpdateInfosCreated() + 1);

        this.statsRepository.save(entity);

    }

}
