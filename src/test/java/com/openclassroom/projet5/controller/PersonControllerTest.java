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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //https://howtodoinjava.com/spring-boot2/testing/spring-boot-mockmvc-example/

    @Test
    public void getPersonsList() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .get("/person")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

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
    public void updatePersonById() throws Exception {
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
        mockMvc.perform(MockMvcRequestBuilders.put("/person/{id}",1).contentType(MediaType.APPLICATION_JSON).content(jsonPerson))
                .andExpect(status().isOk());

    }

    @Test
    public void deletePersonByNames() throws Exception {
        String firstName = "Jack";
        String lastName = "s";
        mockMvc.perform(MockMvcRequestBuilders.delete("/person",1)
                .param("firstName", firstName)
                .param("lastName", lastName)
        ).andExpect(status().isOk());
    }

    @Test
    public void getPersonsByNumberStation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/firestation")
                        .param("stationNumber","2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getChildAlert() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/childAlert")
                        .param("address","2 rue re")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getPhoneAlert() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/phoneAlert")
                        .param("firestation", "2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getPersonsMedicalsByAddress() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/fire")
                        .param("address", "2 rue michelet")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPersonsMedicalsByAddress() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/flood/stations")
                        .param("stations", "1,3")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getPersonsInfo() throws Exception {
        String firstName = "Jack";
        String lastName = "s";
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/personInfo")
                        .param("firstName", firstName)
                        .param("lastName", lastName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void communityEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/communityEmail")
                        .param("city", "torcy")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}