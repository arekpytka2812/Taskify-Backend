package com.pytka.taskifybackend.core.services;

import com.pytka.taskifybackend.core.DTOs.TaskDTO;
import com.pytka.taskifybackend.core.DTOs.UpdateInfoDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface TaskService {

    List<TaskDTO> getTasksByUserID(long userId);

    boolean updateTask(long taskID, TaskDTO taskDTO);

    boolean addTask(long userID, TaskDTO taskDTO);

    boolean addTaskUpdate(long taskID, UpdateInfoDTO updateInfoDTO);
}