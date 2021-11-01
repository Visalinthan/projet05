package com.openclassroom.projet5.controller;

import com.openclassroom.projet5.dto.PersonDto;
import com.openclassroom.projet5.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/person")
    public ResponseEntity<List<PersonDto>> getPersons() {
        List<PersonDto> persons = personService.list();

        return ResponseEntity.ok().body(persons);
    }


    @PostMapping("/person")
    public ResponseEntity<PersonDto> createPerson(@RequestBody  PersonDto personDto) throws Exception {
        if (personDto.getId() != null) {
            throw new Exception("err");
        }

        PersonDto result = personService.save(personDto);

        return ResponseEntity.ok().body(result);
    }


    @PutMapping("/person")
    public ResponseEntity<PersonDto> updatePerson(@RequestBody  PersonDto personDto) throws Exception {
        if (personDto.getId() == null) {
            throw new Exception("err");
        }

        PersonDto result = personService.save(personDto);

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/person/{id}")
    public ResponseEntity<PersonDto> delete(@PathVariable Long id) {
      personService.delete(id);
        return ResponseEntity.ok().build();
    }


}
