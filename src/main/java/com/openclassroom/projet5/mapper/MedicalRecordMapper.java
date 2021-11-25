package com.openclassroom.projet5.mapper;

import com.openclassroom.projet5.dto.MedicalRecordDto;
import com.openclassroom.projet5.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MedicalRecordMapper {

    public MedicalRecord toEntity(List<MedicalRecordDto> medicalRecordDto){
        MedicalRecord medicalRecord = new MedicalRecord();

        for (MedicalRecordDto m : medicalRecordDto) {
            List<Medication> medications = this.insertMedication(m);
            medicalRecord.setMedications(medications);

            List<Allergy> allergies = this.insertAllergy(m);
            medicalRecord.setAllergies(allergies);
        }

        return medicalRecord;
    }
    /*
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
    */
    public MedicalRecord toEntity(Person person, MedicalRecordDto medicalRecordDto) {
        MedicalRecord medicalRecord = new MedicalRecord();

        if(medicalRecordDto.getFirstName().equals(person.getFirstName()) && medicalRecordDto.getLastName().equals(person.getLastName())){
            List<Medication> medications = this.addMedication(person);
            List<Allergy> allergies = this.addAllergy(person);

            person.setMedications(medications);
            person.setAllergys(allergies);
            medicalRecord.setMedications(medications);
            medicalRecord.setAllergies(allergies);
        }


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

    private List<Allergy> addAllergy(Person person) {
        List<Allergy> s = person.getAllergys();
        List<Allergy> allergies = new ArrayList<>();
        for (Allergy allergy : s) {
            Allergy allergy1 = new Allergy();
            allergy1.setName(allergy.getName());
            allergies.add(allergy1);
        }
        return allergies;
    }

    private List<Medication> addMedication(Person person) {
        List<Medication> s = person.getMedications();
        List<Medication> medications = new ArrayList<>();
        if(s.size()<=0){
            for (Medication medication : s) {
                Medication medication1 = new Medication();
                medication1.setName(medication.getName());
                medication1.setDosage(medication.getDosage());

                medications.add(medication1);
            }
        }

        return medications;
    }

    public List<Allergy> insertAllergy(MedicalRecordDto medicalRecordDto) {
        List<String> m = medicalRecordDto.getAllergies();
        List<Allergy> allergies = new ArrayList<>();

        for (String s : m){
            Allergy allergy1 = new Allergy();
            allergy1.setName(s);
            allergies.add(allergy1);
        }
        return allergies;
    }

    private List<Medication> insertMedication(MedicalRecordDto medicalRecordDto) {
        List<String> m = medicalRecordDto.getMedications();
        List<Medication> medications = new ArrayList<>();
        for (String s : m) {
            Medication medication1 = new Medication();
            String[] a = s.split(":", 0);
            medication1.setName(a[0]);
            medication1.setDosage(a[1]);

            medications.add(medication1);
        }

        return medications;
    }


}
