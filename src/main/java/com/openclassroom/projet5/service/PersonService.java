package com.openclassroom.projet5.service;

import com.openclassroom.projet5.dto.PersonDto;
import com.openclassroom.projet5.mapper.PersonMapper;
import com.openclassroom.projet5.model.Person;
import com.openclassroom.projet5.repository.PersonRepository;
import com.openclassroom.projet5.service.exception.PersonNotFound;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public PersonService(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
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





}
