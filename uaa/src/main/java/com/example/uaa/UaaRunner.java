package com.example.uaa;

import com.example.uaa.domain.Account;
import com.example.uaa.domain.Role;
import com.example.uaa.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class UaaRunner implements CommandLineRunner {

    final AccountService accountService;

    @Override
    public void run(String... params) throws Exception {
        Account admin = Account.builder()
                .name("admin")
                .password("admin")
                .email("admin@sample.com")
                .firstName("John")
                .lastName("Doe")
                .roles(new ArrayList<>(Arrays.asList(Role.ROLE_ADMIN)))
                .build();
        this.accountService.signup(admin);

        Account client = Account.builder()
                .name("client")
                .password("pokemon")
                .email("client@sample.com")
                .firstName("John")
                .lastName("Smith")
                .roles(new ArrayList<>(Arrays.asList(Role.ROLE_CLIENT)))
                .build();
        this.accountService.signup(client);
    }
}
