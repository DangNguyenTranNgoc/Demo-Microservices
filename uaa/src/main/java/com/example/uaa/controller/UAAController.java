package com.example.uaa.controller;

import com.example.uaa.domain.Account;
import com.example.uaa.dto.LoginRequest;
import com.example.uaa.dto.SignUpRequest;
import com.example.uaa.dto.UserResponse;
import com.example.uaa.dto.mapper.AccountMapper;
import com.example.uaa.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Api(tags = "users")
public class UAAController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @PostMapping("/signin")
    @ApiOperation(value = "${UAAController.signin}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 422, message = "Invalid username/password supplied")})
    public String login(@ApiParam("Login User") @RequestBody @Valid LoginRequest loginRequest) {
        return this.accountService.signin(loginRequest);
    }

    @PostMapping("/signup")
    @ApiOperation(value = "${UAAController.signup}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Username is already in use")})
    public String signup(@ApiParam("Signup User") @RequestBody @Valid SignUpRequest signUpRequest) {
        return accountService.signup(this.accountMapper.fromSignUpRequestToAccount(signUpRequest));
    }

    @DeleteMapping(value = "/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${UAAController.delete}", authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The user doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public String delete(@ApiParam("Username") @PathVariable @Valid String username) {
        this.accountService.delete(username);
        return username;
    }

    @GetMapping(value = "/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${UAAController.search}",
            response = UserResponse.class, authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The user doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public UserResponse search(@ApiParam("Username") @PathVariable @Valid String username) {
        Account account = this.accountService.search(username);
        return this.accountMapper.fromAccountToUserResponse(account);
    }

    @GetMapping(value = "/me")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${UAAController.me}",
            response = UserResponse.class, authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public UserResponse whoami(HttpServletRequest req) {
        Account account = this.accountService.whoami(req);
        return this.accountMapper.fromAccountToUserResponse(account);
    }

    @GetMapping("/refresh")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${UAAController.refresh}", authorizations = { @Authorization(value="apiKey") })
    public String refresh(HttpServletRequest req) {
        return this.accountService.refresh(req.getRemoteUser());
    }
}
