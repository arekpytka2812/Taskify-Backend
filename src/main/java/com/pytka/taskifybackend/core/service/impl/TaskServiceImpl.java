package com.pytka.taskifybackend.core.service.impl;

import com.pytka.taskifybackend.core.DTO.TaskDTO;
import com.pytka.taskifybackend.core.DTO.UpdateInfoDTO;
import com.pytka.taskifybackend.core.mapper.UpdateInfoMapper;
import com.pytka.taskifybackend.core.model.*;
import com.pytka.taskifybackend.core.service.StatsService;
import com.pytka.taskifybackend.exceptions.core.DataCouldNotBeDeletedException;
import com.pytka.taskifybackend.exceptions.core.DataCouldNotBeSavedException;
import com.pytka.taskifybackend.exceptions.core.DataNotFoundException;
import com.pytka.taskifybackend.exceptions.core.UserNotFoundException;
import com.pytka.taskifybackend.core.mapper.TaskMapper;
import com.pytka.taskifybackend.core.repository.*;
import com.pytka.taskifybackend.core.service.TaskService;
import com.pytka.taskifybackend.scheduling.service.UserStatsSender;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    private final UserRepository userRepository;

    private final UpdateInfoRepository updateInfoRepository;

    private final UpdateInfoMapper updateInfoMapper;

    private final WorkspaceRepository workspaceRepository;

    private final StatsService statsService;

    private final UserStatsSender userStatsSender;

    @Override
    public List<TaskDTO> getTasksByWorkspaceID(Long workspaceID){

        if(!this.workspaceRepository.existsById(workspaceID)){
            throw new DataNotFoundException(WorkspaceEntity.class, workspaceID);
        }

        return this.taskMapper.mapToDTOList(
                this.taskRepository.getTasksByWorkspaceID(workspaceID)
        );

    }

    @Override
    public TaskDTO getTaskByID(Long taskID){

        TaskEntity task = this.taskRepository.findById(taskID)
                .orElseThrow(() ->
                        new DataNotFoundException(TaskEntity.class, taskID)
                );

        return taskMapper.mapToDTO(task);
    }

    @Override
    public void updateTask(Long taskID, TaskDTO taskDTO){

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

    }

    @Override
    public void deleteTask(long taskID){

        TaskEntity task = this.taskRepository.findById(taskID)
                .orElseThrow(() ->
                        new DataNotFoundException(TaskEntity.class, taskID)
                );

        Long userID = this.taskRepository.getUserIDForTask(taskID);

        try{
            this.taskRepository.delete(task);
        }
        catch (DataAccessException e){
            throw new DataCouldNotBeDeletedException(TaskEntity.class, taskID);
        }

        this.statsService.incrementDeletedTaskNumber(userID);

        this.userStatsSender.sendUserStats(userID);

    }

    @Override
    @Transactional
    public void addTask(long userID, TaskDTO taskDTO) {

        if(!this.userRepository.existsById(userID)){
            throw new UserNotFoundException(userID);
        }

        WorkspaceEntity workspaceEntity = this.workspaceRepository.findById(taskDTO.getWorkspaceID())
                .orElseThrow(() ->
                        new DataNotFoundException(WorkspaceEntity.class, taskDTO.getWorkspaceID())
                );

        TaskEntity task = TaskEntity.builder()
                .name(taskDTO.getName())
                .description(taskDTO.getDescription())
                .taskType(taskDTO.getTaskType())
                .priority(taskDTO.getPriority())
                .workspaceID(taskDTO.getWorkspaceID())
                .emailNotifications(taskDTO.getEmailNotifications())
                .appNotifications(taskDTO.getAppNotifications())
                .taskUpdates(updateInfoMapper.mapToEntityList(taskDTO.getTaskUpdates()))
                .expirationDate(taskDTO.getExpirationDate())
                .build();

        try{
            this.taskRepository.save(task);
        }
        catch (DataAccessException e) {
            throw new DataCouldNotBeSavedException(TaskEntity.class, task.getName(), e);
        }

        try{
            this.workspaceRepository.appendTask(workspaceEntity, task);
        }
        catch (DataAccessException e){
            throw new DataCouldNotBeSavedException(WorkspaceEntity.class, workspaceEntity.getName(), e);
        }

        this.statsService.incrementOverallTaskNumber(userID);

        this.userStatsSender.sendUserStats(userID);

    }

    @Override
    @Transactional
    public void addTaskUpdate(long taskID, UpdateInfoDTO updateInfoDTO) {

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

        Long userID = this.taskRepository.getUserIDForTask(taskID);

        this.statsService.incrementCreatedUpdateInfos(userID);

        this.userStatsSender.sendUserStats(userID);

    }

    @Override
    public List<TaskNotification> getTasksExpiringIn(LocalDateTime taskTime){
        return this.taskRepository.getExpiringTasksIn(taskTime);
    }
}
