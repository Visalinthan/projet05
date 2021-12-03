package com.openclassroom.projet5.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    public PersonControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void getPersons() throws Exception{
        mockMvc.perform(get("/person"))
                .andExpect(status().isOk());
    }

    @Test
    public void createPerson() {
    }

    @Test
    public void updatePerson() {
    }

    @Test
    public void updatePersonById() {
    }

    @Test
    public void deletePersonByNames() {
    }

    @Test
    public void getPersonsByNumberStation() {
    }

    @Test
    public void getChildAlert() {
    }

    @Test
    public void getPhoneAlert() {
    }

    @Test
    public void getPersonsMedicalsByAddress() {
    }

    @Test
    public void testGetPersonsMedicalsByAddress() {
    }

    @Test
    public void getPersonsInfo() {
    }

    @Test
    public void communityEmail() {
    }
}