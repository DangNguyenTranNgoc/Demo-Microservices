package com.example.uaa.service;

import com.example.uaa.domain.Account;
import com.example.uaa.exception.CustomException;
import com.example.uaa.repository.AccountRepository;
import com.example.uaa.security.JWTTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public String signin(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username, this.accountRepository
                    .findAccountByName(username).get().getRoles());
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String signup(Account account) {
        try {
            Optional<Account> optionalAccount = this.accountRepository.findAccountByName(account.getName());
            if (!optionalAccount.isPresent()) {
                account.setPassword(passwordEncoder.encode(account.getPassword()));
                this.accountRepository.save(account);
                return this.jwtTokenProvider.createToken(account.getName(), account.getRoles());
            } else {
                throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } catch (Exception ex) {
            throw new CustomException("Exception occur", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public void delete(String username) {
        try {
            Optional<Account> optionalAccount = this.accountRepository.findAccountByName(username);
            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                this.accountRepository.delete(account);
            } else {
                throw new CustomException("Username is not exist", HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } catch (Exception ex) {
            throw new CustomException("Exception occur", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public AppUser search(String username) {
        AppUser appUser = userRepository.findByUsername(username);
        if (appUser == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return appUser;
    }

    public AppUser whoami(HttpServletRequest req) {
        return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
    }

    public String refresh(String username) {
        return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getAppUserRoles());
    }
}
