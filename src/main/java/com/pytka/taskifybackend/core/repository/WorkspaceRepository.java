package com.pytka.taskifybackend.core.repository;

import com.pytka.taskifybackend.core.abstraction.AbstractRepository;
import com.pytka.taskifybackend.core.model.TaskEntity;
import com.pytka.taskifybackend.core.model.WorkspaceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WorkspaceRepository extends AbstractRepository<WorkspaceEntity> {

    @Query("select w from WorkspaceEntity w where w.userID = :userID")
    List<WorkspaceEntity> findAllByUserID(
            @Param("userID") Long userID
    );

    @Transactional
    default void appendTask(WorkspaceEntity entity, TaskEntity value){
        entity.getTasks().add(value);
        this.save(entity);
    }
}
