package com.pytka.taskifybackend.core.controller;

import com.pytka.taskifybackend.core.DTO.WorkspaceDTO;
import com.pytka.taskifybackend.core.DTO.WorkspaceLiteDTO;
import com.pytka.taskifybackend.core.service.WorkspaceService;
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
    public ResponseEntity<Void> addWorkspace(
            @PathVariable("userID") Long userID,
            @RequestBody WorkspaceLiteDTO workspaceDTO
    ) {
        this.workspaceService.addWorkspace(userID, workspaceDTO);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/update/{workspaceID}")
    @RolesAllowed("USER")
    public ResponseEntity<Void> updateWorkspace(
            @PathVariable("workspaceID") Long workspaceID,
            @RequestBody WorkspaceDTO workspaceDTO
    ) {
        this.workspaceService.updateWorkspace(workspaceID, workspaceDTO);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{workspaceID}")
    @RolesAllowed("USER")
    public ResponseEntity<Void> deleteWorkspace(
            @PathVariable("workspaceID") Long workspaceID
    ) {
        this.workspaceService.deleteWorkspace(workspaceID);

        return ResponseEntity.ok().build();
    }

}
