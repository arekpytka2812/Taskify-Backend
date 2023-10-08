package com.pytka.taskifybackend.core.service;

import com.pytka.taskifybackend.core.DTO.WorkspaceDTO;
import com.pytka.taskifybackend.core.DTO.WorkspaceLiteDTO;

import java.util.List;

public interface WorkspaceService {

    List<WorkspaceDTO> getWorkspacesByUserID(Long userID);

    List<WorkspaceLiteDTO> getWorkspacesLiteByUserID(Long userID);

    boolean addWorkspace(Long userID);

    boolean addWorkspace(Long userID, WorkspaceLiteDTO workspaceDTO);

    boolean updateWorkspace(Long workspaceID, WorkspaceDTO workspaceDTO);

    boolean deleteWorkspace(Long workspaceID);
}
