package com.example.uaa.repository;

import com.example.uaa.domain.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @EntityGraph(attributePaths = "roles")
    Optional<Account> findAccountById(Long id);

    @EntityGraph(attributePaths = "roles")
    Optional<Account> findAccountByName(String name);

    @Transactional
    Boolean deleteAccountById(Long id);
}
