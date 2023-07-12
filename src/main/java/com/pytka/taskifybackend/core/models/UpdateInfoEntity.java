package com.pytka.taskifybackend.core.models;

import com.pytka.taskifybackend.core.abstraction.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "UPDATE_INFO")
public class UpdateInfoEntity extends AbstractEntity {

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @Column(name = "description")
    private String description;
}