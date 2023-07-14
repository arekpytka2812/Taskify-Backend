package com.pytka.taskifybackend.core.controllers;

import com.pytka.taskifybackend.core.DTOs.TaskDTO;
import com.pytka.taskifybackend.core.DTOs.UpdateInfoDTO;
import com.pytka.taskifybackend.core.services.TaskService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{userID}")
    @RolesAllowed("USER")
    public ResponseEntity<List<TaskDTO>> getTasksByUserID(
            @PathVariable("userID") long userID
    ) {
        return ResponseEntity.ok(this.taskService.getTasksByUserID(userID));
    }

    //TODO: get task by id

    @PostMapping("/update/{taskID}")
    @RolesAllowed("USER")
    public ResponseEntity<Boolean> updateTask(
            @RequestBody TaskDTO taskDTO,
            @PathVariable("taskID") long taskID
    ) {
        return ResponseEntity.ok(this.taskService.updateTask(taskID, taskDTO));
    }


    //TODO: delete task

    @PostMapping("/add/{userID}")
    @RolesAllowed("USER")
    public ResponseEntity<Boolean> addTask(
            @RequestBody TaskDTO taskDTO,
            @PathVariable("userID") long userID
    ){
        return ResponseEntity.ok(this.taskService.addTask(userID, taskDTO));
    }

    @PostMapping("/updateInfo/{taskID}")
    @RolesAllowed("USER")
    public ResponseEntity<Boolean> addTask(
            @RequestBody UpdateInfoDTO updateInfoDTO,
            @PathVariable("taskID") long taskID
    ){
        return ResponseEntity.ok(this.taskService.addTaskUpdate(taskID, updateInfoDTO));
    }

}
