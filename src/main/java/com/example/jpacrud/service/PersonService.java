package com.example.jpacrud.service;

import com.example.jpacrud.entity.Person;
import com.example.jpacrud.projection.PersonNameAgeProjection;
import com.example.jpacrud.repository.PersonRepository;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private static final Logger logger = LoggerFactory.getLogger(PersonService.class);
    private final PersonRepository personRepository;
    private final MemoryMonitoringService memoryMonitoringService;

    public PersonService(PersonRepository personRepository, MemoryMonitoringService memoryMonitoringService) {
        this.personRepository = personRepository;
        this.memoryMonitoringService = memoryMonitoringService;
    }

    public List<Person> findAll() {
        memoryMonitoringService.logMemoryUsage("before findAll()");
        long start = System.currentTimeMillis();
        List<Person> result = personRepository.findAll();
        long duration = System.currentTimeMillis() - start;
        memoryMonitoringService.logMemoryUsage("after findAll()");
        logger.info("findAll() duration: {} ms, returned {} records", duration, result.size());
        return result;
    }

    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }

    public List<PersonNameAgeProjection> findAllProjected() {
        memoryMonitoringService.logMemoryUsage("before findAllProjected()");
        long start = System.currentTimeMillis();
        List<PersonNameAgeProjection> result = personRepository.findAllBy();
        long duration = System.currentTimeMillis() - start;
        memoryMonitoringService.logMemoryUsage("after findAllProjected()");
        logger.info("findAllProjected() duration: {} ms, returned {} records", duration, result.size());
        return result;
    }
}
