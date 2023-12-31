package com.pytka.taskifybackend.core.service.impl;

import com.pytka.taskifybackend.core.DTO.WorkspaceDTO;
import com.pytka.taskifybackend.core.DTO.WorkspaceLiteDTO;
import com.pytka.taskifybackend.core.repository.TaskRepository;
import com.pytka.taskifybackend.core.service.StatsService;
import com.pytka.taskifybackend.exceptions.core.DataCouldNotBeDeletedException;
import com.pytka.taskifybackend.exceptions.core.DataCouldNotBeSavedException;
import com.pytka.taskifybackend.exceptions.core.DataNotFoundException;
import com.pytka.taskifybackend.exceptions.core.UserNotFoundException;
import com.pytka.taskifybackend.core.mapper.WorkspaceLiteMapper;
import com.pytka.taskifybackend.core.mapper.WorkspaceMapper;
import com.pytka.taskifybackend.core.model.WorkspaceEntity;
import com.pytka.taskifybackend.core.repository.UserRepository;
import com.pytka.taskifybackend.core.repository.WorkspaceRepository;
import com.pytka.taskifybackend.core.service.WorkspaceService;
import com.pytka.taskifybackend.scheduling.service.UserStatsSender;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkspaceServiceImpl implements WorkspaceService {

    private final UserRepository userRepository;

    private final WorkspaceRepository workspaceRepository;

    private final WorkspaceMapper workspaceMapper;

    private final WorkspaceLiteMapper workspaceLiteMapper;

    private final StatsService statsService;

    private final UserStatsSender userStatsSender;

    private final TaskRepository taskRepository;

    @Override
    public List<WorkspaceDTO> getWorkspacesByUserID(Long userID) {

        if(!this.userRepository.existsById(userID)){
            throw new UserNotFoundException(userID);
        }

        return this.workspaceMapper
                .mapToDTOList(
                        this.workspaceRepository.findAllByUserID(userID)
                );
    }

    @Override
    public List<WorkspaceLiteDTO> getWorkspacesLiteByUserID(Long userID){
        if(!this.userRepository.existsById(userID)){
            throw new UserNotFoundException(userID);
        }

        return this.workspaceLiteMapper
                .mapToDTOList(
                        this.workspaceRepository.findAllByUserID(userID)
                );
    }

    @Override
    public void addWorkspace(Long userID) {

        WorkspaceLiteDTO workspaceDTO = WorkspaceLiteDTO.builder()
                .name("First Workspace!")
                .build();

        addWorkspace(userID, workspaceDTO);
    }

    @Override
    public void addWorkspace(Long userID, WorkspaceLiteDTO workspaceDTO) {

        if(!this.userRepository.existsById(userID)){
            throw new UserNotFoundException(userID);
        }

        WorkspaceEntity workspaceEntity = WorkspaceEntity.builder()
                .name(workspaceDTO.getName())
                .userID(userID)
                .build();

        try{
            this.workspaceRepository.save(workspaceEntity);
        }
        catch (DataAccessException e){
            throw new DataCouldNotBeSavedException(WorkspaceEntity.class, workspaceDTO.getName(), e);
        }

        this.statsService.incrementCreatedWorkspaceNumber(userID);

        this.userStatsSender.sendUserStats(userID);

    }

    @Override
    public void updateWorkspace(Long workspaceID, WorkspaceDTO workspaceDTO) {

        WorkspaceEntity workspaceEntity = this.workspaceRepository.findById(workspaceID)
                .orElseThrow(() ->
                        new DataNotFoundException(WorkspaceEntity.class, workspaceID)
                );

        // TODO: change when workspaces and task structures become bigger like muscles
        workspaceEntity.setName(workspaceDTO.getName());

        try{
            this.workspaceRepository.save(workspaceEntity);
        }
        catch (DataAccessException e){
            throw new DataCouldNotBeSavedException(WorkspaceEntity.class, workspaceDTO.getName(), e);
        }

    }

    @Override
    public void deleteWorkspace(Long workspaceID) {

        WorkspaceEntity workspaceEntity = this.workspaceRepository.findById(workspaceID)
                .orElseThrow(() ->
                        new DataNotFoundException(WorkspaceEntity.class, workspaceID)
                );

        Long userID = workspaceEntity.getUserID();

        Long taskCount = taskRepository.getTaskCountPerWorkspace(workspaceID);

        try{
            this.workspaceRepository.delete(workspaceEntity);
        }
        catch(DataAccessException e){
            throw new DataCouldNotBeDeletedException(WorkspaceEntity.class, workspaceID);
        }

        this.statsService.incrementDeletedWorkspaceNumber(userID, 1L);
        this.statsService.incrementDeletedTaskNumber(userID, taskCount);

        this.userStatsSender.sendUserStats(userID);
    }
}
