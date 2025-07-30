package com.example.jpacrud.controller;

import com.example.jpacrud.entity.Person;
import com.example.jpacrud.projection.PersonNameAgeProjection;
import com.example.jpacrud.service.MemoryMonitoringService;
import com.example.jpacrud.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    private final PersonService personService;
    private final MemoryMonitoringService memoryMonitoringService;

    public PersonController(PersonService personService, MemoryMonitoringService memoryMonitoringService) {
        this.personService = personService;
        this.memoryMonitoringService = memoryMonitoringService;
    }

    @GetMapping
    public List<Person> getAllPersons() {
        return personService.findAll();
    }

    @GetMapping("/projection")
    public List<PersonNameAgeProjection> getAllPersonsProjection() {
        return personService.findAllProjected();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        return personService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        return personService.save(person);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person person) {
        return personService.findById(id)
                .map(existing -> {
                    existing.setName(person.getName());
                    existing.setAge(person.getAge());
                    return ResponseEntity.ok(personService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        if (personService.findById(id).isPresent()) {
            personService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/memory")
    public MemoryMonitoringService.MemoryInfo getMemoryUsage() {
        return memoryMonitoringService.getMemoryInfo();
    }

    @PostMapping("/gc")
    public String forceGarbageCollection() {
        memoryMonitoringService.logMemoryUsage("before GC");
        System.gc();
        memoryMonitoringService.logMemoryUsage("after GC");
        return "Garbage collection requested";
    }

}
