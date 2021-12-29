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

    /**
     * Récupère la liste des personne avec la méthode list dans la class personService
     * @return une réponse http avec la liste de personne
     */
    @GetMapping("/person")
    public ResponseEntity<List<PersonDto>> getPersons() {
        List<PersonDto> persons = personService.list();

        Logger.info("Personne listé !");

        return ResponseEntity.ok().body(persons);

    }


    /**
     * Enregitsre une personne avec la méthode save dans la class personService
     * @param personDto
     * @return une réponse http avec la personne enregistré
     * @throws Exception
     */
    @PostMapping("/person")
    public ResponseEntity<PersonDto> createPerson(@RequestBody  PersonDto personDto) throws Exception {
        if (personDto.getId() != null) {
            throw new Exception("erreur ");
        }

        PersonDto result = personService.save(personDto);

        Logger.info("Personne enregisté !");

        return ResponseEntity.ok().body(result);
    }


    /**
     * Modifie une personne avec la méthode update dans la class personService
     * @param id
     * @param personDto
     * @return une réponse http avec la personne modifié
     */
    @PutMapping("/person/{id}")
    public ResponseEntity<PersonDto> updatePersonById(@PathVariable("id") long id,@RequestBody  PersonDto personDto){

        PersonDto result = personService.update(id,personDto);

        Logger.info("La personne avec l'id n° "+id +" a été modifié !");

        if(!personDto.getFirstName().isEmpty() || !personDto.getLastName().isEmpty()){
            Logger.warn("La personne avec le nom "+personDto.getFirstName()+" et le prénom "+personDto.getLastName()+" ne peut pas etre modifié !");
        }


        return ResponseEntity.ok().body(result);
    }

    /**
     * Supprime une personne avec la méthode deleteByNames dans la class personService
     * @param firstName
     * @param lastName
     * @return une réponse http avec la personne supprimé
     */
    @DeleteMapping("/person")
    public ResponseEntity<?> deletePersonByNames(@RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName) {
        personService.deleteByNames(firstName,lastName);

        Logger.warn("La personne avec le nom "+firstName+" et le prénom "+lastName+" a été supprimé !");

        return ResponseEntity.ok().build();
    }

    /**
     * Liste de personne couvert par un numéro de caserne
     * @param StationNumber
     * @return liste de personne
     */
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

    /**
     * Liste d'enfant affiché habitant à l'addresse donnée
     * @param address
     * @return liste de personne
     */
    @RequestMapping(value="childAlert", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<PersonDto>> getChildAlert(@RequestParam("address") String address){
        List<PersonDto> person =  personService.listChildAlert(address);

        Logger.info("Liste d'enfant affiché habitant à l'addresse "+address+ "!");

        return ResponseEntity.ok().body(person);
    }

    /**
     * Liste de n° téléphone des personnes couvert par la numéro de caserne donnée
     * @param StationNumber
     * @return liste de chaine de caractère
     */
    @RequestMapping(value="phoneAlert", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<String>> getPhoneAlert(@RequestParam("firestation") int StationNumber){
        List<String> phone = personService.listPhoneByStationNumber(StationNumber);

        Logger.info("Liste de n° téléphone des personnes couvert par la caserne n° "+StationNumber+ "!");

        return ResponseEntity.ok().body(phone);
    }

    /**
     * Liste de personne habitant à l'adresse donnée
     * @param address
     * @return liste de personne
     */
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

    /**
     * Liste de personne couvert par les numéros de caserne données
     * @param stations
     * @return liste de personne
     */
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

    /**
     * Information sur une personne
     * @param firstName
     * @param lastName
     * @return une personne
     */
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

    /**
     * Liste d' addresse email des personne sur une ville donnée
     * @param city
     * @return liste de chaine de caractère
     */
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
