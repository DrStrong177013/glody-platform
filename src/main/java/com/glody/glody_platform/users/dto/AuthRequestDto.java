package com.glody.glody_platform.users.dto;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String email;
    private String password;
}