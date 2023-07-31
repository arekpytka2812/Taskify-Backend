package com.pytka.taskifybackend.core.repositories;

import com.pytka.taskifybackend.core.DTOs.TaskDTO;
import com.pytka.taskifybackend.core.abstraction.AbstractRepository;
import com.pytka.taskifybackend.core.models.TaskEntity;
import com.pytka.taskifybackend.core.models.UpdateInfoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface TaskRepository extends AbstractRepository<TaskEntity> {

    @Query("select t from TaskEntity t where t.workspaceID = :workspaceID")
    List<TaskEntity> getTasksByWorkspaceID(@Param("workspaceID") Long workspaceID);

    @Transactional
    default void appendUpdateInfo(TaskEntity entity, UpdateInfoEntity value){
        entity.getTaskUpdates().add(value);
        this.save(entity);
    }
}
