package com.pytka.taskifybackend.core.controllers;

import com.pytka.taskifybackend.core.DTOs.TaskDTO;
import com.pytka.taskifybackend.core.DTOs.UpdateInfoDTO;
import com.pytka.taskifybackend.core.DTOs.WorkspaceDTO;
import com.pytka.taskifybackend.core.services.TaskService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/update/{taskID}")
    @RolesAllowed("USER")
    public ResponseEntity<Boolean> updateTask(
            @PathVariable("taskID") Long taskID,
            @RequestBody TaskDTO taskDTO
    ) {
        return ResponseEntity.ok(this.taskService.updateTask(taskID, taskDTO));
    }

    @DeleteMapping("/delete/{taskID}")
    @RolesAllowed("USER")
    public ResponseEntity<Boolean> deleteTask(
            @PathVariable("taskID") long taskID
    ) {
        return ResponseEntity.ok(this.taskService.deleteTask(taskID));
    }

    @PostMapping("/add/{userID}")
    @RolesAllowed("USER")
    public ResponseEntity<Boolean> addTask(
            @PathVariable("userID") long userID,
            @RequestBody TaskDTO taskDTO
    ){
        return ResponseEntity.ok(this.taskService.addTask(userID, taskDTO));
    }

    @PostMapping("/updateInfo/{taskID}")
    @RolesAllowed("USER")
    public ResponseEntity<Boolean> addTaskUpdate(
            @PathVariable("taskID") long taskID,
            @RequestBody UpdateInfoDTO updateInfoDTO
    ){
        return ResponseEntity.ok(this.taskService.addTaskUpdate(taskID, updateInfoDTO));
    }

}
