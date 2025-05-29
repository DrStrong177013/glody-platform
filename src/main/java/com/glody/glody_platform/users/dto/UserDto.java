package com.glody.glody_platform.users.dto;

import lombok.Data;

@Data
public class UserDto {
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private Boolean isExpert = false;
}