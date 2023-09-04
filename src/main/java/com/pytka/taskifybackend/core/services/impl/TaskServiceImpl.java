package com.pytka.taskifybackend.core.services.impl;

import com.pytka.taskifybackend.core.DTOs.TaskDTO;
import com.pytka.taskifybackend.core.DTOs.UpdateInfoDTO;
import com.pytka.taskifybackend.core.exceptions.core.DataCouldNotBeDeletedException;
import com.pytka.taskifybackend.core.exceptions.core.DataCouldNotBeSavedException;
import com.pytka.taskifybackend.core.exceptions.core.DataNotFoundException;
import com.pytka.taskifybackend.core.exceptions.core.UserNotFoundException;
import com.pytka.taskifybackend.core.mappers.TaskMapper;
import com.pytka.taskifybackend.core.mappers.UpdateInfoMapper;
import com.pytka.taskifybackend.core.models.StatsEntity;
import com.pytka.taskifybackend.core.models.TaskEntity;
import com.pytka.taskifybackend.core.models.UpdateInfoEntity;
import com.pytka.taskifybackend.core.models.WorkspaceEntity;
import com.pytka.taskifybackend.core.repositories.*;
import com.pytka.taskifybackend.core.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    private final UserRepository userRepository;

    private final UpdateInfoRepository updateInfoRepository;

    private final WorkspaceRepository workspaceRepository;

    private final StatsRepository statsRepository;

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
    public boolean updateTask(Long taskID, TaskDTO taskDTO){

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

        StatsEntity stats = this.statsRepository.getUserStats(userID)
                .orElseThrow(
                        () -> new DataNotFoundException(StatsEntity.class, userID)
                );

        stats.setTasksCreated(stats.getTasksCreated() + 1);

        try{
            this.statsRepository.save(stats);
        }
        catch (DataAccessException e){
            throw new DataCouldNotBeSavedException(StatsEntity.class, e);
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
