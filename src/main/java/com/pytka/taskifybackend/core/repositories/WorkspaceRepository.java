package com.pytka.taskifybackend.core.repositories;

import com.pytka.taskifybackend.core.abstraction.AbstractRepository;
import com.pytka.taskifybackend.core.models.WorkspaceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkspaceRepository extends AbstractRepository<WorkspaceEntity> {

    @Query("select w from WorkspaceEntity w where w.userID = :userID")
    List<WorkspaceEntity> findAllByUserID(
            @Param("userID") Long userID
    );
}
