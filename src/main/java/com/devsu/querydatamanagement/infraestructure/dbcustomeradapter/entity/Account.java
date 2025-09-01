package com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.util.List;

@Entity
@SQLDelete(sql = "UPDATE account SET status = false WHERE account_id = ?")
@Where(clause = "status = true")
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    private String accountType;

    private BigDecimal initialBalance;

    private BigDecimal availableBalance;

    private boolean status = true;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transactions> transactions;

    @ManyToOne
    @JoinColumn(name = "clientId", nullable = false)
    private Client client;
}
