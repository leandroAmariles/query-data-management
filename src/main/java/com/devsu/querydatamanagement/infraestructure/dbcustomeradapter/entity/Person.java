package com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;

    private String name;

    private String gender;

    private int age;

    private String identification;

    private String address;

    private String phone;
}
