package com.pytka.taskifybackend.core.services.impl;

import com.pytka.taskifybackend.core.DTOs.WorkspaceDTO;
import com.pytka.taskifybackend.core.exceptions.core.DataCouldNotBeDeletedException;
import com.pytka.taskifybackend.core.exceptions.core.DataCouldNotBeSavedException;
import com.pytka.taskifybackend.core.exceptions.core.DataNotFoundException;
import com.pytka.taskifybackend.core.exceptions.core.UserNotFoundException;
import com.pytka.taskifybackend.core.mappers.WorkspaceMapper;
import com.pytka.taskifybackend.core.models.UserEntity;
import com.pytka.taskifybackend.core.models.WorkspaceEntity;
import com.pytka.taskifybackend.core.repositories.UserRepository;
import com.pytka.taskifybackend.core.repositories.WorkspaceRepository;
import com.pytka.taskifybackend.core.services.WorkspaceService;
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
    public boolean addWorkspace(Long userID) {

        WorkspaceDTO workspaceDTO = WorkspaceDTO.builder()
                .name("First Workspace!")
                .build();

        return addWorkspace(userID, workspaceDTO);
    }

    @Override
    public boolean addWorkspace(Long userID, WorkspaceDTO workspaceDTO) {

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

        return true;
    }

    @Override
    public boolean updateWorkspace(Long workspaceID, WorkspaceDTO workspaceDTO) {

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

        return true;
    }

    @Override
    public boolean deleteWorkspace(Long workspaceID) {

        WorkspaceEntity workspaceEntity = this.workspaceRepository.findById(workspaceID)
                .orElseThrow(() ->
                        new DataNotFoundException(WorkspaceEntity.class, workspaceID)
                );

        try{
            this.workspaceRepository.delete(workspaceEntity);
        }
        catch(DataAccessException e){
            throw new DataCouldNotBeDeletedException(WorkspaceEntity.class, workspaceID);
        }

        return true;
    }
}
