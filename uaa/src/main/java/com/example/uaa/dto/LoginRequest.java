package com.example.uaa.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {

    @NotBlank(message = "User name must not empty")
    private String name;

    @NotBlank(message = "User password must not empty")
    private String password;
}
