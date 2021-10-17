package com.example.uaa.service;

import com.example.uaa.domain.Account;
import com.example.uaa.dto.LoginRequest;
import com.example.uaa.exception.CustomException;
import com.example.uaa.repository.AccountRepository;
import com.example.uaa.security.JWTTokenProvider;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public String signin(LoginRequest loginRequest) {
        try {
            log.info("========== Login ==========");
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    loginRequest.getName(),
                    loginRequest.getPassword());
            log.info("====================");
            log.info(token.toString());
            Authentication authentication = this.authenticationManager
                    .authenticate(token);
            log.info(authentication.toString());
            log.info("========== Login successful ==========");
            Account account = this.accountRepository.findAccountByName(loginRequest.getName()).get();
            log.info(account.toString());
            return this.jwtTokenProvider.createToken(account.getName(), account.getRoles());
        } catch (JwtException ex) {
            throw new CustomException("Create token error", HttpStatus.SERVICE_UNAVAILABLE);
        } catch (AuthenticationException e) {
            log.error(e.toString());
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception ex) {
            log.error(ex.toString());
            throw new CustomException("Server error", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @Transactional
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
        } catch (JwtException ex) {
            throw new CustomException("Exception when create JWT token", HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception ex) {
            throw new CustomException("Exception occur", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @Transactional
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

    public Account search(String username) {
        try {
            Optional<Account> optionalAccount = this.accountRepository.findAccountByName(username);
            if (optionalAccount.isPresent()) {
                return optionalAccount.get();
            } else {
                throw new CustomException("Username is not exist", HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } catch (Exception ex) {
            throw new CustomException("Exception occur", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public Account whoami(HttpServletRequest req) {
        try {
            String userName = this.jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
            Optional<Account> optionalAccount = this.accountRepository.findAccountByName(userName);
            if (optionalAccount.isPresent()) {
                return optionalAccount.get();
            } else {
                throw new CustomException("Username is not exist", HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } catch (JwtException ex) {
            throw new CustomException("JWT parsing exception", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            throw new CustomException("Exception occur", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public String refresh(String userName) {
        try {
            Optional<Account> optionalAccount = this.accountRepository.findAccountByName(userName);
            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                return this.jwtTokenProvider.createToken(account.getName(), account.getRoles());
            } else {
                throw new CustomException("Username is not exist", HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } catch (Exception ex) {
            throw new CustomException("Exception occur", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
