package com.pytka.taskifybackend.core.services;

import com.pytka.taskifybackend.core.DTOs.WorkspaceDTO;

import java.util.List;

public interface WorkspaceService {

    List<WorkspaceDTO> getWorkspacesByUserID(Long userID);

    boolean addWorkspace(Long userID);

    boolean addWorkspace(Long userID, WorkspaceDTO workspaceDTO);

    boolean updateWorkspace(Long workspaceID, WorkspaceDTO workspaceDTO);

    boolean deleteWorkspace(Long workspaceID);
}