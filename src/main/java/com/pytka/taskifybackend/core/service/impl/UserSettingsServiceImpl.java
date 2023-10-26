package com.pytka.taskifybackend.core.service.impl;

import com.pytka.taskifybackend.core.DTO.TaskPrioritiesDTO;
import com.pytka.taskifybackend.core.DTO.TaskTypesDTO;
import com.pytka.taskifybackend.core.DTO.UserSettingsDTO;
import com.pytka.taskifybackend.core.mapper.UserSettingsMapper;
import com.pytka.taskifybackend.exceptions.core.DataCouldNotBeSavedException;
import com.pytka.taskifybackend.core.model.UserSettingsEntity;
import com.pytka.taskifybackend.core.repository.UserSettingsRepository;
import com.pytka.taskifybackend.core.service.UserSettingsService;
import com.pytka.taskifybackend.exceptions.core.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSettingsServiceImpl implements UserSettingsService {

    private final UserSettingsRepository userSettingsRepository;

    private final UserSettingsMapper userSettingsMapper;

    @Override
    public void createUserSettingsRecordByUser(Long userID){

        UserSettingsEntity userSettings = UserSettingsEntity.builder()
                .userID(userID)
                .build();

        try{
            this.userSettingsRepository.save(userSettings);
        }
        catch(DataAccessException e){
            throw new DataCouldNotBeSavedException(UserSettingsEntity.class, e);
        }
    }

    @Override
    public UserSettingsDTO getUserSettings(Long userID) {

        UserSettingsEntity entity = this.userSettingsRepository
                .findByUserID(userID)
                .orElseThrow(() -> new DataNotFoundException(UserSettingsEntity.class));


        return this.userSettingsMapper.mapToDTO(entity);
    }

    @Override
    public TaskTypesDTO getTaskTypesForUser(Long userID) {

        List<String> taskTypes =
                this.userSettingsRepository.getTaskTypesForUser(userID);

        return TaskTypesDTO.builder()
                .taskTypes(taskTypes)
                .build();
    }

    @Override
    public TaskPrioritiesDTO getTaskPrioritiesForUser(Long userID) {

        List<String> taskPriorities =
                this.userSettingsRepository.getTaskPrioritiesForUser(userID);

        return TaskPrioritiesDTO.builder()
                .taskPriorities(taskPriorities)
                .build();
    }

    @Override
    public void updateTaskTypes(Long userID, TaskTypesDTO taskTypesDTO) {

        UserSettingsEntity entity = this.userSettingsRepository
                .findByUserID(userID)
                .orElseThrow(() -> new DataNotFoundException(UserSettingsEntity.class));

        entity.setTaskTypes(taskTypesDTO.getTaskTypes());

        this.userSettingsRepository.save(entity);
    }

    @Override
    public void updateTaskPriorities(Long userID, TaskPrioritiesDTO taskPrioritiesDTO) {

        UserSettingsEntity entity = this.userSettingsRepository
                .findByUserID(userID)
                .orElseThrow(() -> new DataNotFoundException(UserSettingsEntity.class));

        entity.setTaskPriorities(taskPrioritiesDTO.getTaskPriorities());

        this.userSettingsRepository.save(entity);
    }

}
