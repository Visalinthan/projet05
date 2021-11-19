package com.openclassroom.projet5.controller;

import com.openclassroom.projet5.dto.PersonDto;
import com.openclassroom.projet5.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


   /* @GetMapping("/person/{id}")
    public ResponseEntity<Optional<PersonDto>> getPersonById(@PathVariable("id") long id) {
        Optional<PersonDto> person = personService.personById(id);
        return ResponseEntity.ok().body(person);
    }*/


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
            throw new Exception("Person id not found !");
        }

        PersonDto result = personService.save(personDto);

        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/person/{id}")
    public ResponseEntity<PersonDto> updatePersonById(@PathVariable("id") long id,@RequestBody  PersonDto personDto) throws Exception {
       /* if (personDto.getId() == null) {
            throw new Exception("Person id not found !");
        }*/

        PersonDto result = personService.update(id,personDto);

        return ResponseEntity.ok().body(result);
    }

   /*@DeleteMapping("/person/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            personService.delete(id);
            return ResponseEntity.ok().build();
        } catch (PersonNotFound e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

    }*/

    @DeleteMapping("/person/{firstName}-{lastName}")
    public ResponseEntity<?> deletePersonByNames(@PathVariable("firstName") String firstName,@PathVariable("lastName") String lastName) {
        personService.deleteByNames(firstName,lastName);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value="firestation", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<PersonDto>> getPersonsByNumberStation(@RequestParam("station_number") int StationNumber){
        List<PersonDto> personDtos =  personService.listPersonByStationNumber(StationNumber);
        personService.countMajorMinor(personDtos);
        return ResponseEntity.ok().body(personDtos);
    }

    @RequestMapping(value="childAlert", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<PersonDto>> getChildAlert(@RequestParam("address") String address){
        List<PersonDto> person =  personService.listChildAlert(address);
        return ResponseEntity.ok().body(person);
    }

    @RequestMapping(value="phoneAlert", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<String>> getPhoneAlert(@RequestParam("firestation") int StationNumber){
        List<String> phone = personService.listPhoneByStationNumber(StationNumber);
        return ResponseEntity.ok().body(phone);
    }


}
