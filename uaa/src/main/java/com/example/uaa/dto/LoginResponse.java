package com.example.uaa.dto;

import com.example.uaa.domain.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class LoginResponse {
    private String name;
    private Set<Role> roles = new HashSet<>();
    private String email;
}