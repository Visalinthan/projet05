package com.openclassroom.projet5.service;

import com.openclassroom.projet5.mapper.MedicalRecordMapper;
import com.openclassroom.projet5.mapper.PersonMapper;
import com.openclassroom.projet5.model.Allergy;
import com.openclassroom.projet5.model.MedicalRecord;
import com.openclassroom.projet5.model.Medication;
import com.openclassroom.projet5.repository.AllergyRepository;
import com.openclassroom.projet5.repository.MedicalRecordRepository;
import com.openclassroom.projet5.repository.MedicationRepository;
import com.openclassroom.projet5.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class MedicalRecordService {

    private MedicalRecordRepository medicalRecordRepository;
    private final PersonMapper personMapper;
    private final MedicalRecordMapper medicalRecordMapper;
    private final PersonRepository personRepository;
    private final AllergyRepository allergyRepository;
    private final MedicationRepository medicationRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository, PersonMapper personMapper, MedicalRecordMapper medicalRecordMapper, PersonRepository personRepository, AllergyRepository allergyRepository, MedicationRepository medicationRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.personMapper = personMapper;
        this.medicalRecordMapper = medicalRecordMapper;
        this.personRepository = personRepository;
        this.allergyRepository = allergyRepository;
        this.medicationRepository = medicationRepository;
    }

    /*public Iterable<MedicalRecord> list(){
        return medicalRecordRepository.findAll();
    }*/

    public MedicalRecord save(MedicalRecord medicalRecord){
        return medicalRecordRepository.save(medicalRecord);
    }

    public Allergy saveAllergy(Allergy allergy){
        return allergyRepository.save(allergy);
    }

    public Medication saveMedication(Medication medication){
        return medicationRepository.save
                (medication);
    }
    public List<Allergy> saveAllergies(List<Allergy> allergy){
        return allergyRepository.saveAll(allergy);
    }

    public List<Medication> saveMedications(List<Medication> medications){
        return medicationRepository.saveAll(medications);
    }


    public Iterable<MedicalRecord> save(Collection<MedicalRecord> medicalRecords) {
        return medicalRecordRepository.saveAll(medicalRecords);
    }
/*
    public List<MedicalRecordDto> list(){
        return personRepository.findAll().stream()
                .map(medicalRecordMapper::toDto)
                .collect(Collectors.toList());
    }

    public MedicalRecordDto save(Long personId, MedicalRecordDto medicalRecordDto){
        Person person = personRepository.findById(personId).stream().findFirst().get();
        MedicalRecord medicalRecord = medicalRecordMapper.toEntity(person,medicalRecordDto);
        medicalRecord = medicalRecordRepository.save(medicalRecord);
        return medicalRecordMapper.toDto(person);
    }
*/
}
