package com.pytka.taskifybackend.auth.tos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    private String email;

    private String oldPassword;

    private String newPassword;
}
