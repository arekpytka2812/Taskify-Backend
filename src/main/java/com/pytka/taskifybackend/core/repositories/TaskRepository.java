package com.pytka.taskifybackend.core.repositories;

import com.pytka.taskifybackend.core.abstraction.AbstractRepository;
import com.pytka.taskifybackend.core.models.TaskEntity;
import com.pytka.taskifybackend.core.models.UpdateInfoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface TaskRepository extends AbstractRepository<TaskEntity> {

    @Query("SELECT te FROM TaskEntity te WHERE te.userID = :userID")
    List<TaskEntity> findAllByUserID(@Param("userID") long userID);

    @Transactional
    default void appendUpdateInfo(TaskEntity entity, UpdateInfoEntity value){
        entity.getTaskUpdates().add(value);
        this.save(entity);
    }
}
