package com.pytka.taskifybackend.core.service;

import com.pytka.taskifybackend.core.DTO.WorkspaceDTO;
import com.pytka.taskifybackend.core.DTO.WorkspaceLiteDTO;

import java.util.List;

public interface WorkspaceService {

    List<WorkspaceDTO> getWorkspacesByUserID(Long userID);

    List<WorkspaceLiteDTO> getWorkspacesLiteByUserID(Long userID);

    void addWorkspace(Long userID);

    void addWorkspace(Long userID, WorkspaceLiteDTO workspaceDTO);

    void updateWorkspace(Long workspaceID, WorkspaceDTO workspaceDTO);

    void deleteWorkspace(Long workspaceID);
}
