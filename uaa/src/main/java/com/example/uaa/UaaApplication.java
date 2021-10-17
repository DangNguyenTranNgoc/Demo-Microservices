package com.example.uaa;

import com.example.uaa.domain.Account;
import com.example.uaa.domain.Role;
import com.example.uaa.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing
public class UaaApplication implements CommandLineRunner {

	final AccountService accountService;

	public static void main(String[] args) {
		SpringApplication.run(UaaApplication.class, args);
	}

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
