package com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.repository;

import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionsRepository extends JpaRepository<Transactions,Long> {

    Optional<Transactions> findByTransactionId(Long id);
}
