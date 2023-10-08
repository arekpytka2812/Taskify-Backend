package com.pytka.taskifybackend.core.repository;

import com.pytka.taskifybackend.core.abstraction.AbstractRepository;
import com.pytka.taskifybackend.core.model.TaskEntity;
import com.pytka.taskifybackend.core.model.UpdateInfoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface TaskRepository extends AbstractRepository<TaskEntity> {

    @Query("select t from TaskEntity t where t.workspaceID = :workspaceID")
    List<TaskEntity> getTasksByWorkspaceID(@Param("workspaceID") Long workspaceID);

    @Query("select w.userID from TaskEntity t left join WorkspaceEntity w on t.workspaceID = w.ID where t.ID = :taskID limit 1")
    Long getUserIDForTask(@Param("taskID") Long taskID);

    @Transactional
    default void appendUpdateInfo(TaskEntity entity, UpdateInfoEntity value){
        entity.getTaskUpdates().add(value);
        this.save(entity);
    }
}
