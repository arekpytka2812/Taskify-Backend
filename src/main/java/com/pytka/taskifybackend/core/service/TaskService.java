package com.pytka.taskifybackend.core.service;

import com.pytka.taskifybackend.core.DTO.TaskDTO;
import com.pytka.taskifybackend.core.DTO.UpdateInfoDTO;
import com.pytka.taskifybackend.core.model.TaskNotification;

import java.time.LocalDateTime;
import java.util.List;


public interface TaskService {

    List<TaskDTO> getTasksByWorkspaceID(Long workspaceID);

    TaskDTO getTaskByID(Long taskID);

    void updateTask(Long taskID, TaskDTO taskDTO);

    void deleteTask(long taskID);

    void addTask(long userID, TaskDTO taskDTO);

    void addTaskUpdate(long taskID, UpdateInfoDTO updateInfoDTO);

    List<TaskNotification> getTasksExpiringIn(LocalDateTime taskTime);
}
