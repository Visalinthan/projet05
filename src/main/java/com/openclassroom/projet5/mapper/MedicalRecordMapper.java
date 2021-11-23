package com.openclassroom.projet5.mapper;

import com.openclassroom.projet5.dto.MedicalRecordDto;
import com.openclassroom.projet5.dto.PersonDto;
import com.openclassroom.projet5.model.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MedicalRecordMapper {

    public MedicalRecordDto toDto(Person person){
        MedicalRecordDto medicalRecordDto = new MedicalRecordDto();
        medicalRecordDto.setFirstName(person.getFirstName());
        medicalRecordDto.setLastName(person.getLastName());
        List<String> medications = this.convertMedication(person.getMedications());
        medicalRecordDto.setMedications(medications);
        List<String> allergies = this.convertAllergy(person.getAllergys());
        medicalRecordDto.setAllergies(allergies);
        return medicalRecordDto;
    }

    public MedicalRecord toEntity(PersonDto personDto, MedicalRecordDto medicalRecordDto) {
        Person person = new Person();
        MedicalRecord medicalRecord = new MedicalRecord();

        List<Medication> medications = this.addMedication(medicalRecordDto);
        person.setMedications(medications);

        List<Allergy> allergies = this.addAllergy(medicalRecordDto);
        person.setAllergys(allergies);

        medicalRecord.setMedications(medications);
        medicalRecord.setAllergies(allergies);

        return medicalRecord;
    }

    private List<String> convertMedication(List<Medication> medication) {
        List<String> medications = new ArrayList<>();
        for (Medication m : medication) {
            medications.add(m.getName());
            medications.add(m.getDosage());
        }
        return medications;
    }

    private List<String> convertAllergy(List<Allergy> allergy) {
        List<String> allergies = new ArrayList<>();
        for (Allergy a : allergy) {
            allergies.add(a.getName());
        }
        return allergies;
    }

    private List<Allergy> addAllergy(MedicalRecordDto medicalRecordDto) {
        List<String> s = medicalRecordDto.getAllergies();
        List<Allergy> allergies = new ArrayList<>();
        for (String allergy : s) {
            Allergy allergy1 = new Allergy();
            allergy1.setName(allergy);
            allergies.add(allergy1);
        }
        return allergies;
    }

    private List<Medication> addMedication(MedicalRecordDto medicalRecordDto) {
        List<String> s = medicalRecordDto.getMedications();
        List<Medication> medications = new ArrayList<>();
        for (String medication : s) {
            Medication medication1 = new Medication();
            String[] m = medication.split(":", 0);
            medication1.setName(m[0]);
            medication1.setDosage(m[1]);

            medications.add(medication1);
        }
        return medications;
    }



}
