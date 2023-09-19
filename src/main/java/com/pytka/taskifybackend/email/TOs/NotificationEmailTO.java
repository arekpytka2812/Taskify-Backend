package com.pytka.taskifybackend.email.TOs;

import com.pytka.taskifybackend.core.DTOs.TaskDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@RequiredArgsConstructor
@SuperBuilder
@Getter
@Setter
public class NotificationEmailTO extends EmailTO {

    private List<TaskDTO> tasks;

}
