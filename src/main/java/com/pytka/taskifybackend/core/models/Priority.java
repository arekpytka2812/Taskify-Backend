package com.pytka.taskifybackend.core.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Priority {
    LOW("LOW"),
    MID("MID"),
    HIGH("HIGH"),
    VERY_HIGH("VERY HIGH");

    private final String priority;
}
