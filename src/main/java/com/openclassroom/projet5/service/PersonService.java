package com.openclassroom.projet5.service;

import com.openclassroom.projet5.dto.PersonDto;
import com.openclassroom.projet5.mapper.PersonMapper;
import com.openclassroom.projet5.model.Person;
import com.openclassroom.projet5.repository.PersonRepository;
import com.openclassroom.projet5.service.exception.PersonNotFound;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Component
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public PersonService(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;;
    }

    /**
     * Sauvergarde dans la base de données d'une personne avec la méthode save de l'interface personRepository
     * @param person
     * @return une sauvergade d'une personne
     */
    public Person save(Person person){
        return personRepository.save(person);
    }

    /**
     * Sauvergarde dans la base de données de plusieurs personnes avec la méthode saveAll de l'interface personRepository
     * @param persons
     * @return une sauvergade des personnes
     */
    public Iterable<Person> save(Collection<Person> persons) {
        return personRepository.saveAll(persons);
    }

    /**
     * Recherche une liste de personne avec la méthode findAll de l'interface personRepository
     * @return une liste personne
     */
    public List<PersonDto> list(){
        return personRepository.findAll().stream()
                .map(personMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Recherche une personne en fonction de l'id dans le paramètre
     * @param id
     * @return une personne
     */
    public Optional<PersonDto> personById(Long id){
        return  personRepository.findById(id).stream()
                .map(personMapper::toDto)
                .findFirst();
    }

    /**
     * Sauvergarde dans la base de données d'une personne DTO converti en entité avec la méthode toEntity
     * @param personDto
     * @return une personne DTO  sauvergardé
     */
    public PersonDto save(PersonDto personDto){
        Person person = personMapper.toEntity(personDto);
        person = personRepository.save(person);
        return personMapper.toDto(person);
    }

    /**
     * Modification dans la base de données d'une personne DTO en récupérant l'id dans le paramètre
     * @param id
     * @param personDto
     * @return une personne DTO modifié
     */
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

    /**
     * Suppression dans la base de données d'une personne en récupérant l'id dans le paramètre
     * @param id
     * @throws PersonNotFound
     */
    public void delete(Long id) throws PersonNotFound {
        try {
            personRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new PersonNotFound(id);
        }
    }

    /**
     * Suppression dans la base de données d'une personne en récupérant le nom et le prénom dans les paramètres
     * @param firstName
     * @param lastName
     */
    public void deleteByNames(String firstName,String lastName){
        personRepository.deleteByNames(firstName,lastName);
    }

    /**
     * Recherche une liste des personnes couvertes par le numéro de caserne de pompiers entrée dans le paramètre.
     * @param StationNumber
     * @return une liste de personne
     */
    public List<PersonDto> listPersonByStationNumber(int StationNumber){
        List<PersonDto> person = personRepository.findByStation(StationNumber).stream()
                .map(personMapper::toDto)
                .collect(Collectors.toList());
        return person;
    }

    /**
     * Compte le nombre de personne majeur dans la liste personne entré dans le paramètre
     * @param personDtos
     * @return le nombre de personne majeur
     */
    public Long countMajor(List<PersonDto> personDtos){
        Long nbMajor = personDtos.stream().map(personMapper::toEntity).filter(person -> person.calculAge(person.getBirthdate())>=18).count();
        return nbMajor ;
    }

    /**
     * Compte le nombre de personne mineur dans la liste personne entré dans le paramètre
     * @param personDtos
     * @return le nombre de personne mineur
     */
    public Long countMinor(List<PersonDto> personDtos){
        Long nbMinor = personDtos.stream().map(personMapper::toEntity).filter(person -> person.calculAge(person.getBirthdate())<=18).count();
        return nbMinor;
    }

    /**
     * Recherche une liste d'enfants habitant à l'adresse entrée dans le paramètre.
     * @param address
     * @return une liste de personne
     */
    public List<PersonDto> listChildAlert(String address){
        return personRepository.findPersonByAddress(address).stream()
                .filter(person -> person.calculAge(person.getBirthdate())<=18)
                .map(personMapper::toDto)
                .collect(Collectors.toList());
    }


    /**
     * Recherche une liste des numéros de téléphone des résidents desservis par la caserne de pompiers entré dans le paramètre
     * @param StationNumber
     * @return une liste de numéro de téléphone
     */
    public List<String> listPhoneByStationNumber(int StationNumber){
        List<String> phoneNumber = personRepository.findByStation(StationNumber).stream()
                .map(person1 -> person1.getPhone())
                .collect(Collectors.toList());
        return phoneNumber;
    }

    /**
     * Recherche une liste de personne habitant à l'addresse indiquée dans le paramètre
     * @param address
     * @return une liste de personne
     */
    public List<PersonDto> listPersonByAddress(String address){
        List<PersonDto> personDto =  personRepository.findPersonByAddress(address).stream()
                .map(personMapper::toDto)
                .collect(Collectors.toList());
        return personDto;
    }


}
