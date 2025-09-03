package com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "PERSON_DEVSU")
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
