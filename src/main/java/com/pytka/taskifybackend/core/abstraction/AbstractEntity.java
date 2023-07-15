package com.pytka.taskifybackend.core.abstraction;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @CreatedDate
    private LocalDateTime createDate = LocalDateTime.now();

    @LastModifiedDate
    private LocalDateTime updateDate;

    @Version
    private Long version;
}
