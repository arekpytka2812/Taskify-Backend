package com.pytka.taskifybackend.core.repositories;

import com.pytka.taskifybackend.core.abstraction.AbstractRepository;
import com.pytka.taskifybackend.core.models.TaskEntity;
import com.pytka.taskifybackend.core.models.UpdateInfoEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface TaskRepository extends AbstractRepository<TaskEntity> {


    @Transactional
    default void appendUpdateInfo(TaskEntity entity, UpdateInfoEntity value){
        entity.getTaskUpdates().add(value);
        this.save(entity);
    }
}
