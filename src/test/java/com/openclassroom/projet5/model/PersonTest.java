package com.openclassroom.projet5.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    private Person person;

    private static LocalDate birthDate = LocalDate.of(1995, 1, 8);
    private static Long id = 1L;

    private static Address getAddress(){
        Address address = new Address();
        address.setId(id);
        address.setAddress("10 rue jo");
        address.setZip(93330);
        address.setCity("lyon");

        return address;
    }

    private static List<Medication> getMedication(){
        List<Medication> medications = new ArrayList<>();
        Medication medication = new Medication();
        medication.setId(id);
        medication.setName("doliprane");
        medication.setDosage("100mg");

        medications.add(medication);
        return medications;
    }
    private static List<Allergy> getAllergy(){
        List<Allergy> allergies = new ArrayList<>();
        Allergy allergy = new Allergy();
        allergy.setId(id);
        allergy.setName("pollen");
        allergies.add(allergy);
        return allergies;
    }

    private static MedicalRecord getMedicalRecord(){
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId(id);
        medicalRecord.setMedications(getMedication());
        medicalRecord.setAllergies(getAllergy());
        return medicalRecord;
    }

    @Test
    public void constructorPerson(){
        person = new Person(id,"paul","george","paul@gmail.com","0612547896",birthDate,getAddress(),getMedicalRecord());
        assertThat(person.getId()).isEqualTo(id);
        assertThat(person.getFirstName()).isEqualTo("paul");
        assertThat(person.getLastName()).isEqualTo("george");
        assertThat(person.getEmail()).isEqualTo("paul@gmail.com");
        assertThat(person.getPhone()).isEqualTo("0612547896");
        assertThat(person.getBirthdate()).isEqualTo(birthDate);
        assertThat(person.getAddress()).isEqualTo(getAddress());
        assertThat(person.getMedicalRecord()).isEqualTo(getMedicalRecord());
    }

}