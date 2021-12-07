package com.openclassroom.projet5.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createPersonValid() throws Exception{
        ObjectMapper obj = new ObjectMapper();
        ObjectNode node = obj.createObjectNode();
        node.set("firstName", TextNode.valueOf("vishal"));
        node.set("lasName", TextNode.valueOf("jo"));
        node.set("email", TextNode.valueOf("mail@gamil.com"));
        node.set("birthdate", TextNode.valueOf("1984-03-06"));
        node.set("address", TextNode.valueOf("2 rue r"));
        node.set("zip", TextNode.valueOf("93330"));
        node.set("city", TextNode.valueOf("ville"));

        String jsonPerson = node.toString();
        mockMvc.perform(MockMvcRequestBuilders.post("/person").contentType(MediaType.APPLICATION_JSON).content(jsonPerson))
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