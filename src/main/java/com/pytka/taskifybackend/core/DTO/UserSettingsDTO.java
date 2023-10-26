package com.pytka.taskifybackend.core.DTO;

import com.pytka.taskifybackend.core.abstraction.AbstractDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class UserSettingsDTO extends AbstractDTO {

    private List<String> taskTypes;

    private List<String> taskPriorities;
}
