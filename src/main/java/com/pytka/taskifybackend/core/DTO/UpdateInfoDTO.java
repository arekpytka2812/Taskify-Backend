package com.pytka.taskifybackend.core.DTO;

import com.pytka.taskifybackend.core.abstraction.AbstractDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class UpdateInfoDTO extends AbstractDTO {

    private LocalDateTime updateInfoDate;

    private String description;
}
