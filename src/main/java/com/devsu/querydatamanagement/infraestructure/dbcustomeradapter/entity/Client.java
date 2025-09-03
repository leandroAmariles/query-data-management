package com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;

import java.util.List;


@Entity
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE account SET status = false WHERE personId = ?")
@PrimaryKeyJoinColumn(name = "personId")
@Table(name = "CLIENT_DEVSU")
@JsonIgnoreProperties({"accounts", "transactions"})
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
