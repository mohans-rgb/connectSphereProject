package com.connectsphere.connectSphereProject.userService.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email, password;
}
