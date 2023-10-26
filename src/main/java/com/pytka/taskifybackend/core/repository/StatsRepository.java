package com.pytka.taskifybackend.core.repository;

import com.pytka.taskifybackend.core.abstraction.AbstractRepository;
import com.pytka.taskifybackend.core.model.StatsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface StatsRepository extends AbstractRepository<StatsEntity> {

    @Query("select s from StatsEntity s where s.userID = :userID")
    Optional<StatsEntity> getUserStats(
            @PathVariable("userID") Long userID
    );


}
