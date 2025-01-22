package com.joaolucas.SCCON.controller;

import com.joaolucas.SCCON.model.Person;
import com.joaolucas.SCCON.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @PostMapping
    public ResponseEntity<String> addPerson(@RequestBody Person person) {
        if (person.getId() == null) {
            person.setId(personService.getAllPersons().stream().mapToLong(Person::getId).max().orElse(0L) + 1);
        } else if (personService.getAllPersons().stream().anyMatch(p -> p.getId().equals(person.getId()))) {
            return new ResponseEntity<>("ID already exists", HttpStatus.CONFLICT);
        }
        personService.addPerson(person);
        return new ResponseEntity<>("Person added successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable Long id) {
        if (personService.getPersonById(id) == null) {
            return new ResponseEntity<>("Person not found", HttpStatus.NOT_FOUND);
        }
        personService.deletePerson(id);
        return new ResponseEntity<>("Person deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePerson(@PathVariable Long id, @RequestBody Person person) {
        if (personService.getPersonById(id) == null) {
            return new ResponseEntity<>("Person not found", HttpStatus.NOT_FOUND);
        }
        personService.updatePerson(id, person);
        return new ResponseEntity<>("Person updated successfully", HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> patchPerson(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        if (personService.getPersonById(id) == null) {
            return new ResponseEntity<>("Person not found", HttpStatus.NOT_FOUND);
        }
        personService.patchPerson(id, updates);
        return new ResponseEntity<>("Person updated successfully", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Person person = personService.getPersonById(id);
        if (person == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping("/{id}/age")
    public ResponseEntity<String> getPersonAge(@PathVariable Long id, @RequestParam String output) {
        int age = personService.getPersonAge(id, output);
        if (age == -1) {
            return new ResponseEntity<>("Invalid output format or person not found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(String.valueOf(age), HttpStatus.OK);
    }

    @GetMapping("/{id}/salary")
    public ResponseEntity<String> getPersonSalary(@PathVariable Long id, @RequestParam String output) {
        double salary = personService.getPersonSalary(id, output);
        if (salary == -1) {
            return new ResponseEntity<>("Invalid output format or person not found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(String.format("%.2f", salary), HttpStatus.OK);
    }
}