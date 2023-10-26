package com.pytka.taskifybackend.core.service;

import com.pytka.taskifybackend.core.DTO.TaskPrioritiesDTO;
import com.pytka.taskifybackend.core.DTO.TaskTypesDTO;
import com.pytka.taskifybackend.core.DTO.UserSettingsDTO;

public interface UserSettingsService {

    void createUserSettingsRecordByUser(Long userID);

    UserSettingsDTO getUserSettings(Long userID);

    TaskTypesDTO getTaskTypesForUser(Long userID);

    TaskPrioritiesDTO getTaskPrioritiesForUser(Long userID);

    void updateTaskTypes(Long userID, TaskTypesDTO taskTypesDTO);

    void updateTaskPriorities(Long userID, TaskPrioritiesDTO taskPrioritiesDTO);

}
