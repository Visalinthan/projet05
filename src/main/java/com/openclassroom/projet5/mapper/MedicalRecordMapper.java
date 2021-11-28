package com.openclassroom.projet5.mapper;

import com.openclassroom.projet5.dto.MedicalRecordDto;
import com.openclassroom.projet5.model.*;
import com.openclassroom.projet5.repository.AllergyRepository;
import com.openclassroom.projet5.repository.MedicationRepository;
import com.openclassroom.projet5.repository.PersonRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MedicalRecordMapper {

    private final AllergyRepository allergyRepository;
    private final MedicationRepository medicationRepository;
    private final PersonRepository personRepository;

    public MedicalRecordMapper(AllergyRepository allergyRepository, MedicationRepository medicationRepository, PersonRepository personRepository) {
        this.allergyRepository = allergyRepository;
        this.medicationRepository = medicationRepository;
        this.personRepository = personRepository;
    }

    public MedicalRecord toEntity(MedicalRecordDto medicalRecordDto){
        MedicalRecord medicalRecord = new MedicalRecord();

        Optional<Person> personExist = personRepository.findByNames(medicalRecordDto.getFirstName(), medicalRecordDto.getLastName());
        personExist.ifPresent(medicalRecord::setPerson);

        List<Medication> medications = this.insertMedication(medicalRecordDto);
        medicalRecord.setMedications(medications);
        List<Allergy> allergies = this.insertAllergy(medicalRecordDto);
        medicalRecord.setAllergies(allergies);

        return medicalRecord;
    }


    public List<Allergy> insertAllergy(MedicalRecordDto medicalRecordDto) {
        List<String> m = medicalRecordDto.getAllergies();
        List<Allergy> allergies = new ArrayList<>();

        for (String s : m) {
            Allergy allergy1 = new Allergy();
            allergy1.setName(s);
            allergies.add(allergy1);
            allergyRepository.save(allergy1);
            allergyRepository.flush();

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

            medicationRepository.save(medication1);
            medicationRepository.flush();

            medications.add(medication1);
        }

        return medications;
    }


}
