package com.openclassroom.projet5.service;

import com.openclassroom.projet5.dto.MedicalRecordDto;
import com.openclassroom.projet5.dto.PersonDto;
import com.openclassroom.projet5.mapper.MedicalRecordMapper;
import com.openclassroom.projet5.mapper.PersonMapper;
import com.openclassroom.projet5.model.FireStation;
import com.openclassroom.projet5.model.Person;
import com.openclassroom.projet5.repository.FireStationRepository;
import com.openclassroom.projet5.repository.MedicalRecordRepository;
import com.openclassroom.projet5.repository.PersonRepository;
import com.openclassroom.projet5.service.exception.PersonNotFound;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonService {

    private final PersonRepository personRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final PersonMapper personMapper;
    private final MedicalRecordMapper medicalRecordMapper;
    private final FireStationRepository fireStationRepository;

    public PersonService(PersonRepository personRepository, MedicalRecordRepository medicalRecordRepository, PersonMapper personMapper, MedicalRecordMapper medicalRecordMapper, FireStationRepository fireStationRepository) {
        this.personRepository = personRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.personMapper = personMapper;
        this.medicalRecordMapper = medicalRecordMapper;
        this.fireStationRepository = fireStationRepository;
    }


    public Person save(Person person){
        return personRepository.save(person);
    }

    public Iterable<Person> save(Collection<Person> persons) {
        return personRepository.saveAll(persons);
    }

    public List<PersonDto> list(){
        return personRepository.findAll().stream()
                .map(personMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public Optional<PersonDto> personById(Long id){
        return  personRepository.findById(id).stream()
                .map(personMapper::toDto)
                .findFirst();
    }

    public PersonDto save(PersonDto personDto){
        Person person = personMapper.toEntity(personDto);
        person = personRepository.save(person);
        return personMapper.toDto(person);
    }

    public PersonDto update(Long id,PersonDto personDto){
        Optional<PersonDto> personFind = personRepository.findById(id).stream()
                .map(personMapper::toDto)
                .findFirst();

        PersonDto updatedPerson = null;

        if (personFind.isPresent()) {
            PersonDto personUpdate = personFind.get();
            personUpdate.setFirstName(personDto.getFirstName());
            personUpdate.setLastName(personDto.getLastName());
            personUpdate.setEmail(personDto.getEmail());
            personUpdate.setBirthdate(personDto.getBirthdate());
            personUpdate.setPhone(personDto.getPhone());
            personUpdate.setAddress(personDto.getAddress());
            personUpdate.setCity(personDto.getCity());
            personUpdate.setZip(personDto.getZip());
            Person person = personMapper.toEntity(personUpdate);
            person = personRepository.save(person);
            updatedPerson = personMapper.toDto(person);
        }
        return updatedPerson;
    }

    public void delete(Long id) throws PersonNotFound {
        try {
            personRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new PersonNotFound(id);
        }
    }

    public void deleteByNames(String firstName,String lastName){
        personRepository.deleteByNames(firstName,lastName);
    }


    public List<PersonDto> listPersonByStationNumber(int StationNumber){
        List<PersonDto> person = personRepository.findByStation(StationNumber).stream()
                .map(personMapper::toDto)
                .collect(Collectors.toList());
        return person;
    }

    public String countMajorMinor(List<PersonDto> personDtos){
        Long nbMinor = personDtos.stream().map(personMapper::toEntity).filter(person -> person.CalculAge(person.getBirthdate())<=18).count();
        Long nbMajor = personDtos.stream().map(personMapper::toEntity).filter(person -> person.CalculAge(person.getBirthdate())>=18).count();
        return "Nombre d'adultes : "+ nbMajor + " Nombre d'enfants : "+ nbMinor;
    }

    public List<PersonDto> listChildAlert(String address){
        return personRepository.findPersonByAddress(address).stream()
                .filter(person -> person.CalculAge(person.getBirthdate())<=18)
                .map(personMapper::toDto)
                .collect(Collectors.toList());
    }


    public List<String> listPhoneByStationNumber(int StationNumber){
        List<String> phoneNumber = personRepository.findByStation(StationNumber).stream()
                .map(person1 -> person1.getPhone())
                .collect(Collectors.toList());
        return phoneNumber;
    }


    public List<Object> listPersonMedicalByAddress(String address){
        List<Object> obj = new ArrayList<>();
        List<PersonDto> personDto =  personRepository.findPersonByAddress(address).stream()
                .map(personMapper::toDto)
                .collect(Collectors.toList());

        for (PersonDto p :personDto){
            Optional<MedicalRecordDto> m = medicalRecordRepository.findByPerson(p.getFirstName(),p.getLastName()).stream()
                    .map(medical -> medicalRecordMapper.toDto(medical))
                    .findFirst();
            obj.add("Person : " + p);
            if(m.isPresent()) {
                obj.add("Medications : " + m.get().getMedications());
                obj.add("Allergies : " + m.get().getAllergies());
            }
        }
        int station = fireStationRepository.findFireStationByAddress(address);
        String s = "Le n° station est "+ station;

        obj.add(s);

        return obj;
    }


}
