package com.pytka.taskifybackend.core.services;

import com.pytka.taskifybackend.core.DTOs.StatsDTO;

public interface StatsService {

    StatsDTO getUserStats(Long userID);

    boolean addUserStats(Long userID);

}
