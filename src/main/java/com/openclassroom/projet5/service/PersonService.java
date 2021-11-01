package com.openclassroom.projet5.service;

import com.openclassroom.projet5.dto.PersonDto;
import com.openclassroom.projet5.mapper.PersonMapper;
import com.openclassroom.projet5.model.Person;
import com.openclassroom.projet5.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    private final PersonMapper personMapper;

    public PersonService(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    public List<PersonDto> list(){
       return personRepository.findAll().stream()
                .map(personMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public Person save(Person person){
        return personRepository.save(person);
    }

    public PersonDto save(PersonDto personDto){
        Person person = personMapper.toEntity(personDto);
        person = personRepository.save(person);
        return personMapper.toDto(person);
    }

    public void delete(Long id){
        personRepository.deleteById(id);
    }

    public Iterable<Person> save(Collection<Person> persons) {
        return personRepository.saveAll(persons);
    }

}
