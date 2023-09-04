package com.pytka.taskifybackend.core.controllers;


import com.pytka.taskifybackend.core.DTOs.StatsDTO;
import com.pytka.taskifybackend.core.services.StatsService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stats")
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/{userID}")
    @RolesAllowed("USER")
    public ResponseEntity<StatsDTO> getUserStats(
            @PathVariable("userID") Long userID
    ){
        return ResponseEntity.ok(
                this.statsService.getUserStats(userID)
        );
    }



}
