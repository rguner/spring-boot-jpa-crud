package com.example.jpacrud.repository;

import com.example.jpacrud.entity.Person;
import com.example.jpacrud.projection.PersonNameAgeProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<PersonNameAgeProjection> findAllBy();
}
