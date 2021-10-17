package com.example.uaa.dto;

import com.example.uaa.domain.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LoginResponse {
    private String name;
    private List<Role> roles;
    private String email;
}
