package com.example.uaa.dto.mapper;

import com.example.uaa.domain.Account;
import com.example.uaa.dto.LoginResponse;
import com.example.uaa.dto.UserResponse;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {
    LoginResponse fromAccountToLoginResponse(Account account);
    UserResponse fromAccountToUserResponse(Account account);

}
