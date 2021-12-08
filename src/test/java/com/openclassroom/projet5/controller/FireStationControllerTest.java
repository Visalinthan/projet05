package com.openclassroom.projet5.controller;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void getFireStations() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/firestations")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createFireStation() throws Exception {
        ObjectMapper obj = new ObjectMapper();
        ObjectNode node = obj.createObjectNode();

        node.set("address", TextNode.valueOf("2 rue r"));
        node.set("zip", TextNode.valueOf("93330"));
        node.set("city", TextNode.valueOf("ville"));
        node.set("station", TextNode.valueOf("5"));

        String jsonFireStation = node.toString();
        mockMvc.perform(MockMvcRequestBuilders.post("/firestation").contentType(MediaType.APPLICATION_JSON).content(jsonFireStation))
                .andExpect(status().isOk());
    }

    @Test
    void updateFireStation() {
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation/{id}",83)
        ).andExpect(status().isOk());
    }
}