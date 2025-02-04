package com.example.CRUD;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/persons")
public class PersonneController {

    final PersonneRepository personneRepository;

    public PersonneController(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }

    @GetMapping()
    public ResponseEntity<List<Person>>getAllPersons(){
        return new ResponseEntity<>(personneRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Person> createPerson(@RequestBody Person person){
        return new ResponseEntity<>(personneRepository.save(person), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id){
        Optional<Person> person = personneRepository.findById(id);
        return person.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person, @PathVariable Long id){
        Optional<Person> person1 = personneRepository.findById(id);
        if (person1.isPresent()) {
            Person personExiste = person1.get();
            personExiste.setName(person.getName());
            personExiste.setCity(person.getCity());
            personExiste.setPhoneNumber(person.getPhoneNumber());
            Person PersonUpdated =  personneRepository.save(personExiste);
            return new ResponseEntity<>(PersonUpdated,HttpStatus.CREATED);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id){
        Optional<Person> person = personneRepository.findById(id);
        if (person.isPresent()) {
            personneRepository.delete(person.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
