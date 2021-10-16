package com.example.uaa.domain;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
@ToString
public enum Role implements GrantedAuthority {
    ROLE_ADMIN("admin"),
    ROLE_CLIENT("client");

    public final String label;

    @Override
    public String getAuthority() {
        return name();
    }
}
