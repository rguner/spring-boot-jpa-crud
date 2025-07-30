package com.example.jpacrud.projection;

import org.springframework.beans.factory.annotation.Value;

public interface PersonNameAgeProjection {
    @Value("#{target.name}")
    String getName();
    @Value("#{target.age}")
    int getAge();
    @Value("#{target.lastName}")
    String getLastName();
    @Value("#{target.employeeId}")
    String getEmployeeId();
    @Value("#{target.address}")
    String getAddress();
    @Value("#{target.telephone}")
    String getTelephone();
}
