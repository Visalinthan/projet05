package com.openclassroom.projet5.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MedicalRecordTest {

    private MedicalRecord medicalRecord;

    private static Long id = 1L;
    private static LocalDate birthDate = LocalDate.of(1995, 1, 8);

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

    @Test
    public void constructorMedicalRecord(){
        medicalRecord = new MedicalRecord(id,getMedication(),getAllergy(),getPerson());
        assertThat(medicalRecord.getId()).isEqualTo(id);
        assertThat(medicalRecord.getMedications()).isEqualTo(getMedication());
        assertThat(medicalRecord.getAllergies()).isEqualTo(getAllergy());
        assertThat(medicalRecord.getPerson()).isEqualTo(getPerson());
    }

}