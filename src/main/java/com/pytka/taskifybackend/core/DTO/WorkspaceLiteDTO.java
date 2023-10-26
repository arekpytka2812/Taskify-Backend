package com.pytka.taskifybackend.core.DTO;

import com.pytka.taskifybackend.core.abstraction.AbstractDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class WorkspaceLiteDTO extends AbstractDTO {

    private String name;

}
