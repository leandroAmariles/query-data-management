package com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
public class Client extends Person {

    @Column(unique = true, nullable = false)
    private String clientId;

    private String password;

    private boolean status;

    @OneToMany(mappedBy = "client")
    private List<Account> accounts;


    @OneToMany(mappedBy = "client")
    private List<Transactions> transactions;

}
