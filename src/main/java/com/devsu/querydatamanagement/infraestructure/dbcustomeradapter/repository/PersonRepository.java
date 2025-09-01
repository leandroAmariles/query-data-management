package com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.repository;

import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
