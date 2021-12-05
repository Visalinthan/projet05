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

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

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

        List<Person> person = new ArrayList<>();
        person.add(getPerson());

        when(personRepository.findAll()).thenReturn(person);
        when(personMapper.toDto((Person) any())).thenReturn(getPersonDto());

        assertThat(personService.list()).isNotEmpty();
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
        PersonDto personDto = getPersonDto();
        doNothing().when(personRepository).deleteByNames(personDto.getFirstName(),personDto.getLastName());

        personService.deleteByNames(personDto.getFirstName(),personDto.getLastName());

        verify(personRepository, times(1)).deleteByNames(personDto.getFirstName(),personDto.getLastName());
    }
/*
    @Test
    public void personById() {
    }

    @Test
    public void listPersonByStationNumber() {
    }

    @Test
    public void countMajorMinor() {
    }

    @Test
    public void listChildAlert() {
    }

    @Test
    public void listPhoneByStationNumber() {
    }

    @Test
    public void listPersonByAddress() {
    }*/
}