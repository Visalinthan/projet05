package com.openclassroom.projet5.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.openclassroom.projet5.model.Allergy;
import com.openclassroom.projet5.model.Medication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordDto {

    private String firstName;
    private String lastName;
    @JsonProperty("birthdate")
    private String birthDate;
    private List<String> medications;
    private List<String> allergies;



}
