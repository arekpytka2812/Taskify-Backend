package com.pytka.taskifybackend.core.services.impl;

import com.pytka.taskifybackend.core.DTOs.TaskDTO;
import com.pytka.taskifybackend.core.DTOs.UpdateInfoDTO;
import com.pytka.taskifybackend.core.exceptions.core.DataCouldNotBeSavedException;
import com.pytka.taskifybackend.core.exceptions.core.DataNotFoundException;
import com.pytka.taskifybackend.core.exceptions.core.UserNotFoundException;
import com.pytka.taskifybackend.core.mappers.TaskMapper;
import com.pytka.taskifybackend.core.models.TaskEntity;
import com.pytka.taskifybackend.core.models.UpdateInfoEntity;
import com.pytka.taskifybackend.core.models.UserEntity;
import com.pytka.taskifybackend.core.repositories.TaskRepository;
import com.pytka.taskifybackend.core.repositories.UserRepository;
import com.pytka.taskifybackend.core.services.TaskService;
import lombok.AllArgsConstructor;
import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository,
                           UserRepository userRepository,
                           TaskMapper taskMapper
    ) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<TaskDTO> getTasksByUserID(long userID) {

        if(!this.userRepository.existsById(userID)){
            throw new UserNotFoundException(userID);
        }

        return this.taskMapper
                .mapToDTOList(this.taskRepository.findAllByUserID(userID));
    }

    @Override
    public boolean updateTask(long taskID, TaskDTO taskDTO){

        return true;
    }

    @Override
    @Transactional
    public boolean addTask(long userID, TaskDTO taskDTO) {

        if(!this.userRepository.existsById(userID)){
            throw new UserNotFoundException(userID);
        }

        TaskEntity task = TaskEntity.builder()
                .name(taskDTO.getName())
                .description(taskDTO.getDescription())
                .taskType(taskDTO.getTaskType())
                .userID(userID)
                .priority(taskDTO.getPriority())
                .taskUpdates(taskDTO.getTaskUpdates())
                .build();

        try{
            this.taskRepository.save(task);
        }
        catch (DataAccessException e) {
            throw new DataCouldNotBeSavedException(TaskEntity.class, task.getName(), e);
        }

        return true;
    }

    @Override
    @Transactional
    public boolean addTaskUpdate(long taskID, UpdateInfoDTO updateInfoDTO) {

        TaskEntity task = this.taskRepository.findById(taskID)
                .orElseThrow(() ->
                        new DataNotFoundException(taskID)
                );

        UpdateInfoEntity updateInfo = UpdateInfoEntity.builder()
                .description(updateInfoDTO.getDescription())
                .updateDate(updateInfoDTO.getUpdateDate())
                .build();

        try{
            this.taskRepository.appendUpdateInfo(task, updateInfo);
        }
        catch (DataAccessException e){
            throw new DataCouldNotBeSavedException(UpdateInfoEntity.class, e);
        }

        return true;
    }
}
