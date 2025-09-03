package com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.repository;

import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {


    Optional<Account> findAccountByAccountId(Long accountId);

    boolean existsAccountByAccountNumber(String accountNumber);

}
