package com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Entity
@Getter
@Setter
@Table(name = "TRANSACTIONS_DEVSU")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private LocalDateTime transactionDate = LocalDateTime.parse(LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));

    private String transactionType;

    private String amount;

    private String balance;

    @ManyToOne
    @JoinColumn(name = "accountId", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "personId", nullable = false)
    private Client client;

}
