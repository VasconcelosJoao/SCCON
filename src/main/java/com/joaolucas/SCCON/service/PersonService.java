package com.joaolucas.SCCON.service;

import com.joaolucas.SCCON.model.Person;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class PersonService {
    private final Map<Long, Person> personMap = new HashMap<>();

    @PostConstruct
    public void init() {
        personMap.put(1L, new Person(1L, "José da Silva", LocalDate.of(2000, 4, 6), LocalDate.of(2020, 5, 10)));
        personMap.put(2L, new Person(2L, "Maria Oliveira", LocalDate.of(1995, 8, 15), LocalDate.of(2018, 3, 20)));
        personMap.put(3L, new Person(3L, "Carlos Pereira", LocalDate.of(1988, 12, 30), LocalDate.of(2015, 7, 1)));
    }

    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>(personMap.values());
        persons.sort(Comparator.comparing(Person::getName));
        return persons;
    }

    public void addPerson(Person person) {
        personMap.put(person.getId(), person);
    }

    public void deletePerson(Long id) {
        personMap.remove(id);
    }

    public void updatePerson(Long id, Person person) {
        personMap.put(id, person);
    }

    public void patchPerson(Long id, Map<String, Object> updates) {
        Person person = personMap.get(id);
        if (person != null) {
            updates.forEach((key, value) -> {
                switch (key) {
                    case "name":
                        person.setName((String) value);
                        break;
                    case "birthDate":
                        person.setBirthDate(LocalDate.parse((String) value));
                        break;
                    case "admissionDate":
                        person.setAdmissionDate(LocalDate.parse((String) value));
                        break;
                }
            });
        }
    }

    public Person getPersonById(Long id) {
        return personMap.get(id);
    }

    public int getPersonAge(Long id, String output) {
        Person person = personMap.get(id);
        if (person == null) {
            return -1;
        }
        LocalDate birthDate = person.getBirthDate();
        switch (output) {
            case "days":
                return (int) ChronoUnit.DAYS.between(birthDate, LocalDate.now());
            case "months":
                return (int) ChronoUnit.MONTHS.between(birthDate, LocalDate.now());
            case "years":
                return (int) ChronoUnit.YEARS.between(birthDate, LocalDate.now());
            default:
                return -1;
        }
    }

    public double getPersonSalary(Long id, String output) {
        Person person = personMap.get(id);
        if (person == null) {
            return -1;
        }
        LocalDate admissionDate = person.getAdmissionDate();
        int yearsWorked = Period.between(admissionDate, LocalDate.now()).getYears();
        double salary = 1558.00;
        for (int i = 0; i < yearsWorked; i++) {
            salary += salary * 0.18 + 500;
        }
        if ("full".equals(output)) {
            return Math.ceil(salary * 100) / 100;
        } else if ("min".equals(output)) {
            return Math.ceil((salary / 1302.00) * 100) / 100;
        } else {
            return -1;
        }
    }
}