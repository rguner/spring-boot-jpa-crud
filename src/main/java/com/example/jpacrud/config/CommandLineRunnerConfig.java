package com.example.jpacrud.config;

import com.example.jpacrud.entity.Person;
import com.example.jpacrud.repository.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.IntStream;

@Configuration
public class CommandLineRunnerConfig {

    private final PersonRepository personRepository;

    public CommandLineRunnerConfig(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            System.out.println("Application has started. CommandLineRunner is executing.");
            IntStream.range(0, 10000).forEach(i -> {
                Person person = new Person();
                person.setName("Person " + i);
                person.setAddress("Address " + i);
                person.setEmployeeId("Employee " + i);
                person.setAge(i);
                person.setTelephone("Telephone " + i) ;
                personRepository.save(person);
            });
        };
    }
}