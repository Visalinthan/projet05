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
import org.tinylog.Logger;

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

        Logger.info("Personne listé !");

        return ResponseEntity.ok().body(persons);

    }


    @PostMapping("/person")
    public ResponseEntity<PersonDto> createPerson(@RequestBody  PersonDto personDto) throws Exception {
        if (personDto.getId() != null) {
            throw new Exception("err");
        }

        PersonDto result = personService.save(personDto);

        Logger.info("Personne enregisté !");

        return ResponseEntity.ok().body(result);
    }


    @PutMapping("/person/{id}")
    public ResponseEntity<PersonDto> updatePersonById(@PathVariable("id") long id,@RequestBody  PersonDto personDto){

        PersonDto result = personService.update(id,personDto);

        Logger.info("La personne avec l'id n° "+id +" a été modifié !");

        return ResponseEntity.ok().body(result);
    }


    @DeleteMapping("/person")
    public ResponseEntity<?> deletePersonByNames(@RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName) {
        personService.deleteByNames(firstName,lastName);

        Logger.warn("La personne avec le nom "+firstName+" et le prénom "+lastName+" a été supprimé !");

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value="firestation", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<Object>> getPersonsByNumberStation(@RequestParam("stationNumber") int StationNumber){
        List<PersonDto> personDtos =  personService.listPersonByStationNumber(StationNumber);
        Long nbMajor = personService.countMajor(personDtos);
        Long nbMinor = personService.countMinor(personDtos);
        String countMajor = "Nombre d'adultes : "+ nbMajor;
        String countMinor = "Nombre d'enfants : "+ nbMinor;
        List<Object> result = new ArrayList<>();
        for (PersonDto p : personDtos){
            result.add(p);
        }
        result.add(countMajor);
        result.add(countMinor);

        Logger.info("Liste de personne couvert par la caserne n° "+StationNumber+ "!");

        return ResponseEntity.ok().body(result);
    }

    @RequestMapping(value="childAlert", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<PersonDto>> getChildAlert(@RequestParam("address") String address){
        List<PersonDto> person =  personService.listChildAlert(address);

        Logger.info("Liste d'enfant affiché habitant à l'addresse "+address+ "!");

        return ResponseEntity.ok().body(person);
    }

    @RequestMapping(value="phoneAlert", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<String>> getPhoneAlert(@RequestParam("firestation") int StationNumber){
        List<String> phone = personService.listPhoneByStationNumber(StationNumber);

        Logger.info("Liste de n° téléphone des personnes couvert par la caserne n° "+StationNumber+ "!");

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
            Long age = person.calculAge(p.getBirthdate());
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

        Logger.info("Liste de personne habitant à l'adresse° "+address+ "!");

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

        Logger.info("Liste de personne couvert par la caserne n° "+stations+ "!");

        return ResponseEntity.ok().body(result);
    }

    @RequestMapping(value="personInfo", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<Object>> getPersonsInfo(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName){
        List<Object> result = new ArrayList<>();
        List<PersonDto> persons = personService.list();

        for (PersonDto p : persons){
            List<Object> obj = new ArrayList<>();
            if(p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)){
                obj.add(p.getFirstName() +" "+p.getLastName());
                obj.add(p.getEmail());

                Person person = new Person();
                Long age = person.calculAge(p.getBirthdate());
                obj.add(age +" ans");

                Optional<MedicalRecordDto> medicalRecordDto = medicalRecordService.listMedicalByNames(p.getFirstName(), p.getLastName());

                if(medicalRecordDto.isPresent()) {
                    obj.add("Medications : " + medicalRecordDto.get().getMedications());
                    obj.add("Allergies : " + medicalRecordDto.get().getAllergies());
                }
                result.add(obj);
            }

        }

        Logger.info("Info de "+firstName+ " "+lastName+" !");

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

        Logger.info("Liste d' addresse email des personne habitant  à "+city+ "!");

        return ResponseEntity.ok().body(result);
    }

}
