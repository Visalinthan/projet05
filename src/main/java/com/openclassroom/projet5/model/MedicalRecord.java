package com.openclassroom.projet5.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MedicalRecord {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Medication> medications = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Allergy> allergies = new ArrayList<>();

    @JoinColumn(name = "person_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;


}
