package com.pytka.taskifybackend.core.controller;

import com.pytka.taskifybackend.auth.service.AuthService;
import com.pytka.taskifybackend.core.DTO.TaskPrioritiesDTO;
import com.pytka.taskifybackend.core.DTO.TaskTypesDTO;
import com.pytka.taskifybackend.core.DTO.UserSettingsDTO;
import com.pytka.taskifybackend.core.service.UserSettingsService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/settings")
@RequiredArgsConstructor
public class UserSettingsController {

    private final UserSettingsService userSettingsService;

    private final AuthService authService;

    @GetMapping("/{userID}")
    @RolesAllowed("USER")
    public ResponseEntity<UserSettingsDTO> getUserSettings(
            @PathVariable(name = "userID") Long userID
    ) {
        return ResponseEntity.ok(
                this.userSettingsService.getUserSettings(userID)
        );
    }

    @GetMapping("/types/{userID}")
    @RolesAllowed("USER")
    public ResponseEntity<TaskTypesDTO> getTaskTypesForUser(
            @PathVariable(name = "userID") Long userID
    ) {
        return ResponseEntity.ok(
                this.userSettingsService.getTaskTypesForUser(userID)
        );
    }

    @GetMapping("/priorities/{userID}")
    @RolesAllowed("USER")
    public ResponseEntity<TaskPrioritiesDTO> getTaskPrioritiesForUser(
            @PathVariable(name = "userID") Long userID
    ) {
        return ResponseEntity.ok(
                this.userSettingsService.getTaskPrioritiesForUser(userID)
        );
    }

    @PostMapping("/types/{userID}")
    @RolesAllowed("USER")
    public ResponseEntity<Void> updateTaskTypes(
            @PathVariable(name = "userID") Long userID,
            @RequestBody TaskTypesDTO taskTypesDTO
    ){
        this.userSettingsService.updateTaskTypes(userID, taskTypesDTO);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/priorities/{userID}")
    @RolesAllowed("USER")
    public ResponseEntity<Void> updateTaskPriorities(
            @PathVariable(name = "userID") Long userID,
            @RequestBody TaskPrioritiesDTO taskPrioritiesDTO
    ){
        this.userSettingsService.updateTaskPriorities(userID, taskPrioritiesDTO);

        return ResponseEntity.ok().build();
    }
}
