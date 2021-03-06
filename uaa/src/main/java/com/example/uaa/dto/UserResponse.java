package com.example.uaa.dto;

import com.example.uaa.domain.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserResponse {
    @ApiModelProperty(position = 0)
    private String name;
    @ApiModelProperty(position = 1)
    private List<Role> roles;
    @ApiModelProperty(position = 2)
    private String email;
    @ApiModelProperty(position = 3)
    private String firstName;
    @ApiModelProperty(position = 4)
    private String lastName;
}
