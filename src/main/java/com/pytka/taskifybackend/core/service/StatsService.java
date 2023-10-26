package com.pytka.taskifybackend.core.service;

import com.pytka.taskifybackend.core.DTO.StatsDTO;

public interface StatsService {

    StatsDTO getUserStats(Long userID);

    boolean addUserStats(Long userID);

    void updateStats(Long userID, StatsDTO statsDTO);

    void incrementCreatedWorkspaceNumber(Long userID);

    void incrementDeletedWorkspaceNumber(Long userID, Long count);

    void incrementOverallTaskNumber(Long userID);

    void incrementDeletedTaskNumber(Long userID, Long count);

    void incrementCreatedUpdateInfos(Long userID);


}
