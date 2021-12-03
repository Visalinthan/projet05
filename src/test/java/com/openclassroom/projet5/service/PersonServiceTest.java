package com.openclassroom.projet5.service;

import com.openclassroom.projet5.controller.PersonController;
import com.openclassroom.projet5.dto.PersonDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class PersonServiceTest {

    @MockBean
    public PersonService personService;

    @Test
    void save() {
    }

    @Test
    void testSave() {
    }

    @Test
    void list() {
        LocalDate birthDate = LocalDate.of(1995, 1, 8);
        Long id = 141L;
        PersonDto personDto = new PersonDto(id,"TOM","Danny","tom@gmail.com","0125639865",birthDate,"10 rue jo",93330,"lyon");
        personService.save(personDto);
        List<PersonDto> personDtoList = personService.list();
        assertEquals("TOM",personDtoList.get(1).getFirstName());
    }

    @Test
    void personById() {
    }

    @Test
    void testSave1() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteByNames() {
    }

    @Test
    void listPersonByStationNumber() {
    }

    @Test
    void countMajorMinor() {
    }

    @Test
    void listChildAlert() {
    }

    @Test
    void listPhoneByStationNumber() {
    }

    @Test
    void listPersonByAddress() {
    }
}