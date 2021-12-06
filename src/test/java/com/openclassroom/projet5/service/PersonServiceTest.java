package com.openclassroom.projet5.service;

import com.openclassroom.projet5.dto.PersonDto;
import com.openclassroom.projet5.mapper.PersonMapper;
import com.openclassroom.projet5.model.Address;
import com.openclassroom.projet5.model.Person;
import com.openclassroom.projet5.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;


    private static LocalDate birthDate = LocalDate.of(1995, 1, 8);
    private static Long id = 1L;

    private static Person getPerson(){
        Address address = new Address();
        Person person = new Person();
        person.setId(id);
        person.setFirstName("TOM");
        person.setLastName("Danny");
        person.setEmail("tom@gmail.com");
        person.setPhone("0125639865");
        person.setBirthdate(birthDate);
        address.setId(1L);
        address.setAddress("10 rue jo");
        address.setZip(93330);
        address.setCity("lyon");
        person.setAddress(address);

        return person;
    }

    private static PersonDto getPersonDto(){
        PersonDto personDto = new PersonDto();
        personDto.setId(id);
        personDto.setFirstName("TOM");
        personDto.setLastName("Danny");
        personDto.setEmail("tom@gmail.com");
        personDto.setPhone("0125639865");
        personDto.setBirthdate(birthDate);
        personDto.setAddress("10 rue jo");
        personDto.setZip(93330);
        personDto.setCity("lyon");

        return personDto;
    }


    @Test
    public void list() {

        List<Person> personList = new ArrayList<>();
        personList.add(getPerson());

        when(personRepository.findAll()).thenReturn(personList);
        when(personMapper.toDto((Person) any())).thenReturn(getPersonDto());

        assertThat(personService.list().get(0)).isEqualTo(getPersonDto());
    }

    @Test
    public void save() {
        PersonDto personDto = getPersonDto();

        when(personMapper.toEntity((PersonDto) any())).thenReturn(getPerson());
        when(personRepository.save(any())).thenReturn(getPerson());
        when(personMapper.toDto((Person) any())).thenReturn(getPersonDto());

        assertThat(personService.save(personDto)).isEqualTo(personDto);
    }

    @Test
    public void update() {
        PersonDto personDto = getPersonDto();

        when(personRepository.findById(personDto.getId())).thenReturn(Optional.of(getPerson()));
        when(personMapper.toDto((Person) any())).thenReturn(getPersonDto());

        assertThat(personService.update(personDto.getId(),personDto)).isEqualTo(personDto);
    }

    @Test
    public void deleteByNames() {
        PersonDto personDtoList = getPersonDto();
        doNothing().when(personRepository).deleteByNames(personDtoList.getFirstName(),personDtoList.getLastName());

        personService.deleteByNames(personDtoList.getFirstName(),personDtoList.getLastName());

        verify(personRepository, times(1)).deleteByNames(personDtoList.getFirstName(),personDtoList.getLastName());
    }

    @Test
    public void personById() {
        List<PersonDto> personDtoList = new ArrayList<>();
        personDtoList.add(getPersonDto());

        when(personRepository.findById(personDtoList.get(0).getId())).thenReturn(Optional.of(getPerson()));
        when(personMapper.toDto((Person) any())).thenReturn(getPersonDto());

        assertThat(personService.personById(1L)).isEqualTo(Optional.of(getPersonDto()));

    }


    @Test
    public void listPersonByStationNumber() {
        List<Person> personList = new ArrayList<>();
        personList.add(getPerson());

        when(personRepository.findByStation(anyInt())).thenReturn(personList);
        when(personMapper.toDto((Person) any())).thenReturn(getPersonDto());

        assertThat(personService.listPersonByStationNumber(1).get(0)).isEqualTo(getPersonDto());

    }


    @Test
    public void countMajorMinor() {
        List<PersonDto> personDtoList = new ArrayList<>();
        List<Person> personList = new ArrayList<>();
        personDtoList.add(getPersonDto());
        personList.add(getPerson());
        Long nbMinor ;
        Long nbMajor;

        when(personMapper.toEntity((PersonDto) any())).thenReturn(getPerson());
        nbMinor = personList.stream().filter(person -> person.CalculAge(person.getBirthdate())<=18).count();
        nbMajor = personList.stream().filter(person -> person.CalculAge(person.getBirthdate())>=18).count();

        assertThat(personService.countMajorMinor(personDtoList)).isEqualTo("Nombre d'adultes : "+ nbMajor + " Nombre d'enfants : "+ nbMinor);
    }


    @Test
    public void listChildAlert() {
        List<Person> personList = new ArrayList<>();
        personList.add(getPerson());

        when(personRepository.findPersonByAddress(anyString())).thenReturn(personList);

        assertThat(personService.listChildAlert("10 rue jo")).isEqualTo(personList.stream().filter(person1 -> person1.CalculAge(person1.getBirthdate())<=18).collect(Collectors.toList()));
    }

    @Test
    public void listPhoneByStationNumber() {
        List<Person> person = new ArrayList<>();
        person.add(getPerson());

        when(personRepository.findByStation(anyInt())).thenReturn(person);

        assertThat(personService.listPhoneByStationNumber(1)).isEqualTo(person.stream().map(p -> p.getPhone()).collect(Collectors.toList()));
    }

    @Test
    public void listPersonByAddress() {
        List<PersonDto> personDtoList = new ArrayList<>();
        List<Person> personList = new ArrayList<>();
        personDtoList.add(getPersonDto());
        personList.add(getPerson());

        when(personRepository.findPersonByAddress(anyString())).thenReturn(personList);
        when(personMapper.toDto((Person) any())).thenReturn(getPersonDto());

        assertThat(personService.listPersonByAddress("10 rue jo")).isEqualTo(personDtoList);
    }


}