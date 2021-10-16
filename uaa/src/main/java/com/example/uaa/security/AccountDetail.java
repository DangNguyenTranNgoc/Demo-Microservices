package com.example.uaa.security;

import com.example.uaa.domain.Account;
import com.example.uaa.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountDetail implements UserDetailsService {

  private final AccountRepository accountRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final Optional<Account> optAccount = accountRepository.findAccountByName(username);

    if (! optAccount.isPresent()) {
      throw new UsernameNotFoundException("User '" + username + "' not found");
    }
    Account account = optAccount.get();

    return org.springframework.security.core.userdetails.User//
        .withUsername(username)//
        .password(account.getPassword())//
        .authorities(account.getRoles())//
        .accountExpired(false)//
        .accountLocked(false)//
        .credentialsExpired(false)//
        .disabled(false)//
        .build();
  }

}
