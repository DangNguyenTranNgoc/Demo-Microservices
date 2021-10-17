package com.example.uaa.repository;

import com.example.uaa.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findAccountById(Long id);

    Optional<Account> findAccountByName(String name);

    @Transactional
    Boolean deleteAccountById(Long id);
}
