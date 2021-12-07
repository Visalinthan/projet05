package com.openclassroom.projet5.controller;

import com.openclassroom.projet5.dto.MedicalRecordDto;
import com.openclassroom.projet5.dto.PersonDto;
import com.openclassroom.projet5.model.Address;
import com.openclassroom.projet5.model.Person;
import com.openclassroom.projet5.service.AddressService;
import com.openclassroom.projet5.service.FireStationService;
import com.openclassroom.projet5.service.MedicalRecordService;
import com.openclassroom.projet5.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class PersonController {

    private final PersonService personService;
    private final MedicalRecordService medicalRecordService;
    private final FireStationService fireStationService;
    private final AddressService addressService;

    public PersonController(PersonService personService, MedicalRecordService medicalRecordService, FireStationService fireStationService, AddressService addressService) {
        this.personService = personService;
        this.medicalRecordService = medicalRecordService;
        this.fireStationService = fireStationService;
        this.addressService = addressService;
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


    @DeleteMapping("/person/{firstName}-{lastName}")
    public ResponseEntity<?> deletePersonByNames(@PathVariable("firstName") String firstName,@PathVariable("lastName") String lastName) {
        personService.deleteByNames(firstName,lastName);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value="firestation", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<Object>> getPersonsByNumberStation(@RequestParam("stationNumber") int StationNumber){
        List<PersonDto> personDtos =  personService.listPersonByStationNumber(StationNumber);
        Long nbMajor = personService.countMajor(personDtos);
        Long nbMinor = personService.countMinor(personDtos);
        String countAge = "Nombre d'adultes : "+ nbMajor + " Nombre d'enfants : "+ nbMinor;
        List<Object> result = new ArrayList<>();
        for (PersonDto p : personDtos){
            result.add(p);
        }
        result.add(countAge);
        return ResponseEntity.ok().body(result);
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

    @RequestMapping(value="fire", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<Object>> getPersonsMedicalsByAddress(@RequestParam("address") String address){
        List<Object> result = new ArrayList<>();
        List<PersonDto> personDto =  personService.listPersonByAddress(address);

        for (PersonDto p : personDto){
            List<Object> obj = new ArrayList<>();
            Optional<MedicalRecordDto> medicalRecordDto = medicalRecordService.listMedicalByNames(p.getFirstName(), p.getLastName());

            obj.add(p.getFirstName() +" "+ p.getLastName());
            obj.add(p.getPhone());
            Person person = new Person();
            Long age = person.CalculAge(p.getBirthdate());
            obj.add(age +" ans");
            if(medicalRecordDto.isPresent()) {
                obj.add("Medications : " + medicalRecordDto.get().getMedications());
                obj.add("Allergies : " + medicalRecordDto.get().getAllergies());
            }
            int station= fireStationService.stationNumberByAddress(address);
            String s = "N° station "+station;
            obj.add(s);
            result.add(obj);
        }


        return ResponseEntity.ok().body(result);
    }

    @RequestMapping(value="flood/stations", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<Object>> getPersonsMedicalsByAddress(@RequestParam("stations") List<Integer> stations){
        List<Object> result = new ArrayList<>();
        List<Address> addresses = addressService.listAddressByStations(stations);
        for (Address a :addresses){
            result.add(this.getPersonsMedicalsByAddress(a.getAddress()).getBody());
        }

        return ResponseEntity.ok().body(result);
    }

    @RequestMapping(value="personInfo", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<Object>> getPersonsInfo(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName){
        List<Object> result = new ArrayList<>();
        List<PersonDto> persons = personService.list();

        for (PersonDto p : persons){
            List<Object> obj = new ArrayList<>();
            if(p.getFirstName().equals(firstName) || p.getLastName().equals(lastName)){
                obj.add(p.getFirstName() +" "+p.getLastName());
                obj.add(p.getEmail());

                Person person = new Person();
                Long age = person.CalculAge(p.getBirthdate());
                obj.add(age +" ans");

                Optional<MedicalRecordDto> medicalRecordDto = medicalRecordService.listMedicalByNames(p.getFirstName(), p.getLastName());

                if(medicalRecordDto.isPresent()) {
                    obj.add("Medications : " + medicalRecordDto.get().getMedications());
                    obj.add("Allergies : " + medicalRecordDto.get().getAllergies());
                }
                result.add(obj);
            }

        }

        return ResponseEntity.ok().body(result);
    }

    @RequestMapping(value="communityEmail", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<String>> communityEmail(@RequestParam("city") String city){
        List<String> result = new ArrayList<>();
        List<PersonDto> persons = personService.list();

        for (PersonDto p : persons){
            if(p.getCity().equals(city)){
                result.add(p.getEmail());
            }
        }

        return ResponseEntity.ok().body(result);
    }


}
