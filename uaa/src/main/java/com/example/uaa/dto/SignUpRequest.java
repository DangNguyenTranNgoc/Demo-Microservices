package com.example.uaa.dto;

import com.example.uaa.domain.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
public class SignUpRequest {
    @ApiModelProperty(position = 0)
    @NotEmpty
    private String name;
    @ApiModelProperty(position = 1)
    @NotEmpty
    private String password;
    @ApiModelProperty(position = 2)
    @NotEmpty
    private List<Role> roles;
    @ApiModelProperty(position = 3)
    private String email;
    @ApiModelProperty(position = 4)
    private String firstName;
    @ApiModelProperty(position = 5)
    private String lastName;
}
