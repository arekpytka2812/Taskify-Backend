package com.pytka.taskifybackend.core.controllers;

import com.pytka.taskifybackend.core.DTOs.WorkspaceDTO;
import com.pytka.taskifybackend.core.DTOs.WorkspaceLiteDTO;
import com.pytka.taskifybackend.core.services.WorkspaceService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workspace")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @GetMapping("/{userID}")
    @RolesAllowed("USER")
    public ResponseEntity<List<WorkspaceDTO>> getWorkspacesByUserID(
            @PathVariable("userID") Long userID
    ) {
        return ResponseEntity.ok(this.workspaceService.getWorkspacesByUserID(userID));
    }

    @GetMapping("/lite/{userID}")
    @RolesAllowed("USER")
    public ResponseEntity<List<WorkspaceLiteDTO>> getWorkspacesLiteByUserID(
            @PathVariable("userID") Long userID
    ) {
        return ResponseEntity.ok(this.workspaceService.getWorkspacesLiteByUserID(userID));
    }

    @PostMapping("/add/{userID}")
    @RolesAllowed("USER")
    public ResponseEntity<Boolean> addWorkspace(
            @PathVariable("userID") Long userID,
            @RequestBody WorkspaceDTO workspaceDTO
    ) {
        return ResponseEntity.ok(this.workspaceService.addWorkspace(userID, workspaceDTO));
    }

    @PostMapping("/update/{workspaceID}")
    @RolesAllowed("USER")
    public ResponseEntity<Boolean> updateWorkspace(
            @PathVariable("workspaceID") Long workspaceID,
            @RequestBody WorkspaceDTO workspaceDTO
    ) {
        return ResponseEntity.ok(this.workspaceService.updateWorkspace(workspaceID, workspaceDTO));
    }

    @DeleteMapping("/delete/{workspaceID}")
    @RolesAllowed("USER")
    public ResponseEntity<Boolean> deleteWorkspace(
            @PathVariable("workspaceID") Long workspaceID
    ) {
        return ResponseEntity.ok(this.workspaceService.deleteWorkspace(workspaceID));
    }

}
