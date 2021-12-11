package com.openclassroom.projet5.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getMedicalRecords() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/medicalRecord")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createMedicalRecord() throws Exception {
        String firstName = "John";
        String lastName = "Boyd";

        ObjectMapper obj = new ObjectMapper();
        ObjectNode node = obj.createObjectNode();
        ArrayNode array = obj.createArrayNode();
        node.set("allergies",array.add("pollen"));

        String jsonMedicalRecord = node.toString();
        mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord")
                        .param("firstName", firstName)
                        .param("lastName", lastName)
                        .contentType(MediaType.APPLICATION_JSON).content(jsonMedicalRecord))
                .andExpect(status().isOk());
    }

    @Test
    void updateMedicalRecord() throws Exception {
        String firstName = "John";
        String lastName = "Boyd";

        ObjectMapper obj = new ObjectMapper();
        ObjectNode node = obj.createObjectNode();
        ArrayNode array = obj.createArrayNode();
        node.set("allergies",array.add("pollen"));

        String jsonMedicalRecord = node.toString();
        mockMvc.perform(MockMvcRequestBuilders.put("/medicalRecord")
                        .param("firstName", firstName)
                        .param("lastName", lastName)
                        .contentType(MediaType.APPLICATION_JSON).content(jsonMedicalRecord))
                .andExpect(status().isOk());
    }

    @Test
    void deletePersonByNames() throws Exception {
 /*       String firstName = "Jack";
        String lastName = "s";
        mockMvc.perform(MockMvcRequestBuilders.delete("/medicalRecord",1)
                .param("firstName", firstName)
                .param("lastName", lastName)
        ).andExpect(status().isOk());*/
    }
}