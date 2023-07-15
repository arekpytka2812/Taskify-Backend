package com.pytka.taskifybackend.core.services.impl;

import com.pytka.taskifybackend.core.DTOs.TaskDTO;
import com.pytka.taskifybackend.core.DTOs.UpdateInfoDTO;
import com.pytka.taskifybackend.core.exceptions.core.DataCouldNotBeDeletedException;
import com.pytka.taskifybackend.core.exceptions.core.DataCouldNotBeSavedException;
import com.pytka.taskifybackend.core.exceptions.core.DataNotFoundException;
import com.pytka.taskifybackend.core.exceptions.core.UserNotFoundException;
import com.pytka.taskifybackend.core.mappers.TaskMapper;
import com.pytka.taskifybackend.core.mappers.UpdateInfoMapper;
import com.pytka.taskifybackend.core.models.TaskEntity;
import com.pytka.taskifybackend.core.models.UpdateInfoEntity;
import com.pytka.taskifybackend.core.repositories.TaskRepository;
import com.pytka.taskifybackend.core.repositories.UpdateInfoRepository;
import com.pytka.taskifybackend.core.repositories.UserRepository;
import com.pytka.taskifybackend.core.services.TaskService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    private final TaskMapper taskMapper;
    private final UpdateInfoMapper updateInfoMapper;

    private final UpdateInfoRepository updateInfoRepository;

    public TaskServiceImpl(TaskRepository taskRepository,
                           UserRepository userRepository,
                           TaskMapper taskMapper,
                           UpdateInfoMapper updateInfoMapper,
                           UpdateInfoRepository updateInfoRepository
    ) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
        this.updateInfoMapper = updateInfoMapper;
        this.updateInfoRepository = updateInfoRepository;
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

        TaskEntity task = this.taskRepository.findById(taskID)
                .orElseThrow(() ->
                        new DataNotFoundException(TaskEntity.class, taskID)
                );

        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setTaskType(taskDTO.getTaskType());
        task.setPriority(taskDTO.getPriority());

        try{
            this.taskRepository.save(task);
        }
        catch (DataAccessException e) {
            throw new DataCouldNotBeSavedException(TaskEntity.class, task.getName(), e);
        }

        return true;
    }

    @Override
    public boolean deleteTask(long taskID){

        TaskEntity task = this.taskRepository.findById(taskID)
                .orElseThrow(() ->
                        new DataNotFoundException(TaskEntity.class, taskID)
                );

        try{
            this.taskRepository.delete(task);
        }
        catch (DataAccessException e){
            throw new DataCouldNotBeDeletedException(TaskEntity.class, taskID);
        }

        return true;
    }

    @Override
    @Transactional
    public boolean addTask(long userID, TaskDTO taskDTO) {

        if(!this.userRepository.existsById(userID)){
            throw new UserNotFoundException(userID);
        }

        List<UpdateInfoEntity> updateInfoEntities = this.updateInfoMapper
                .mapToEntityList(taskDTO.getTaskUpdates());

        TaskEntity task = TaskEntity.builder()
                .name(taskDTO.getName())
                .description(taskDTO.getDescription())
                .taskType(taskDTO.getTaskType())
                .userID(userID)
                .priority(taskDTO.getPriority())
                .taskUpdates(updateInfoEntities)
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
                        new DataNotFoundException(TaskEntity.class, taskID)
                );

        UpdateInfoEntity updateInfo = UpdateInfoEntity.builder()
                .description(updateInfoDTO.getDescription())
                .updateInfoDate(updateInfoDTO.getUpdateInfoDate())
                .build();

        try{
            this.updateInfoRepository.save(updateInfo);
        }
        catch (DataAccessException e){
            throw new DataCouldNotBeSavedException(UpdateInfoEntity.class, e);
        }

        try{
            this.taskRepository.appendUpdateInfo(task, updateInfo);
        }
        catch (DataAccessException e){
            throw new DataCouldNotBeSavedException(TaskEntity.class, e);
        }

        return true;
    }
}
