package com.pytka.taskifybackend.core.service;

import com.pytka.taskifybackend.core.DTO.TaskDTO;
import com.pytka.taskifybackend.core.DTO.UpdateInfoDTO;

import java.util.List;


public interface TaskService {

    List<TaskDTO> getTasksByWorkspaceID(Long workspaceID);

    TaskDTO getTaskByID(Long taskID);

    boolean updateTask(Long taskID, TaskDTO taskDTO);

    boolean deleteTask(long taskID);

    boolean addTask(long userID, TaskDTO taskDTO);

    boolean addTaskUpdate(long taskID, UpdateInfoDTO updateInfoDTO);
}
