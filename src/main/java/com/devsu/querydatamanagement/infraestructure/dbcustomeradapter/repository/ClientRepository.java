package com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.repository;

import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findClientByPersonId(Long personId);

    boolean existsByIdentification(String identification);
}
