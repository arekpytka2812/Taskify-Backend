package com.pytka.taskifybackend.scheduling.service.impl;

import com.pytka.taskifybackend.core.DTO.StatsDTO;
import com.pytka.taskifybackend.core.service.StatsService;
import com.pytka.taskifybackend.scheduling.service.UserStatsSender;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserStatsSenderImpl implements UserStatsSender {

    private final StatsService statsService;

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendUserStats(Long userID){

        String userChannel = "/user/" + userID + "/stats";
        StatsDTO stats = this.statsService.getUserStats(userID);

        System.out.println("\n\n[DEBUG - STATS] Sent user stats!\n\n");

        messagingTemplate.convertAndSend(userChannel, stats);
    }

}
