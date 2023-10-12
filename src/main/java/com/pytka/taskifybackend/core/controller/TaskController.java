package com.pytka.taskifybackend.core.controller;

import com.pytka.taskifybackend.core.DTO.TaskDTO;
import com.pytka.taskifybackend.core.DTO.UpdateInfoDTO;
import com.pytka.taskifybackend.core.service.TaskService;
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

    @GetMapping("/getAll/{workspaceID}")
    @RolesAllowed("USER")
    public ResponseEntity<List<TaskDTO>> getTasksByWorkspaceID(
            @PathVariable("workspaceID") Long workspaceID
    ) {
        return ResponseEntity.ok(this.taskService.getTasksByWorkspaceID(workspaceID));
    }

    @GetMapping("/get/{taskID}")
    @RolesAllowed("USER")
    public ResponseEntity<TaskDTO> getTaskByID(
            @PathVariable("taskID") Long taskID
    ) {
        return ResponseEntity.ok(this.taskService.getTaskByID(taskID));
    }

    @PostMapping("/update/{taskID}")
    @RolesAllowed("USER")
    public ResponseEntity<Void> updateTask(
            @PathVariable("taskID") Long taskID,
            @RequestBody TaskDTO taskDTO
    ) {
        this.taskService.updateTask(taskID, taskDTO);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{taskID}")
    @RolesAllowed("USER")
    public ResponseEntity<Void> deleteTask(
            @PathVariable("taskID") long taskID
    ) {
        this.taskService.deleteTask(taskID);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/add/{userID}")
    @RolesAllowed("USER")
    public ResponseEntity<Void> addTask(
            @PathVariable("userID") long userID,
            @RequestBody TaskDTO taskDTO
    ){
        this.taskService.addTask(userID, taskDTO);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/updateInfo/{taskID}")
    @RolesAllowed("USER")
    public ResponseEntity<Void> addTaskUpdate(
            @PathVariable("taskID") long taskID,
            @RequestBody UpdateInfoDTO updateInfoDTO
    ){
        this.taskService.addTaskUpdate(taskID, updateInfoDTO);

        return ResponseEntity.ok().build();
    }

}
