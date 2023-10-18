package com.pytka.taskifybackend.core.repository;

import com.pytka.taskifybackend.core.abstraction.AbstractRepository;
import com.pytka.taskifybackend.core.model.UserSettingsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserSettingsRepository extends AbstractRepository<UserSettingsEntity> {

    Optional<UserSettingsEntity> findByUserID(Long userID);

    @Query( "select s.taskTypes " +
            "from UserSettingsEntity s " +
            "where s.userID = :userID")
    List<String> getTaskTypesForUser(
            @Param("userID") Long userID
    );

    @Query( "select s.taskPriorities " +
            "from UserSettingsEntity s " +
            "where s.userID = :userID")
    List<String> getTaskPrioritiesForUser(
            @Param("userID") Long userID
    );
}
