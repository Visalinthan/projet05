package com.openclassroom.projet5.mapper;

import com.openclassroom.projet5.dto.MedicalRecordDto;
import com.openclassroom.projet5.dto.PersonDto;
import com.openclassroom.projet5.model.*;
import com.openclassroom.projet5.repository.AllergyRepository;
import com.openclassroom.projet5.repository.MedicalRecordRepository;
import com.openclassroom.projet5.repository.MedicationRepository;
import com.openclassroom.projet5.repository.PersonRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MedicalRecordMapper {

    private final MedicalRecordRepository medicalRecordRepository;
    private final AllergyRepository allergyRepository;
    private final MedicationRepository medicationRepository;
    private final PersonRepository personRepository;
    private final PersonMapper personMapper ;

    public MedicalRecordMapper(MedicalRecordRepository medicalRecordRepository, AllergyRepository allergyRepository, MedicationRepository medicationRepository, PersonRepository personRepository, PersonMapper personMapper) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.allergyRepository = allergyRepository;
        this.medicationRepository = medicationRepository;
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    public MedicalRecordDto toDto(MedicalRecord medicalRecord){
        MedicalRecordDto medicalRecordDto = new MedicalRecordDto();

        medicalRecordDto.setFirstName(medicalRecord.getPerson().getFirstName());
        medicalRecordDto.setLastName(medicalRecord.getPerson().getLastName());
        medicalRecordDto.setBirthDate(medicalRecord.getPerson().getBirthdate().toString());
        List<String> allergies = convertAllergies(medicalRecord.getAllergies());
        medicalRecordDto.setAllergies(allergies);
        List<String> medications = convertMedications(medicalRecord.getMedications());
        medicalRecordDto.setMedications(medications);

        return medicalRecordDto;
    }


    public MedicalRecord toEntity(MedicalRecordDto medicalRecordDto){
        MedicalRecord medicalRecord = new MedicalRecord();

        Optional<Person> personExist = personRepository.findByNames(medicalRecordDto.getFirstName(), medicalRecordDto.getLastName());
        if(personExist.isPresent()){
            medicalRecord.setPerson(personExist.get());
        }

        Optional<MedicalRecord> medicalRecordExist = medicalRecordRepository.findByPerson(medicalRecordDto.getFirstName(), medicalRecordDto.getLastName());

        List<Medication> medications = new ArrayList<>();

        List<Allergy> allergies = new ArrayList<>();

        if (medicalRecordExist.isPresent()){
            medicalRecord.setId(medicalRecordExist.get().getId());
            if(medicalRecordDto.getMedications() != null){
                medications = this.addMedication(medicalRecordDto);
            }
            if(medicalRecordDto.getAllergies() != null) {
                allergies = this.addAllergy(medicalRecordDto);
            }
            for (Medication medication : medicalRecordExist.get().getMedications()){
                medications.add(medication);
            }
            for (Allergy allergy : medicalRecordExist.get().getAllergies()){
                allergies.add(allergy);
            }
            medicalRecord.setMedications(medications);
            medicalRecord.setAllergies(allergies);
        }else{
            medications = this.addMedication(medicalRecordDto);
            medicalRecord.setMedications(medications);
            allergies = this.addAllergy(medicalRecordDto);
            medicalRecord.setAllergies(allergies);
        }

        return medicalRecord;
    }

    public List<String> convertAllergies(List<Allergy> allergies){
        List<String> allergys = new ArrayList<>();
        for (Allergy a : allergies){
            allergys.add(a.getName());
        }

        return allergys;
    }

    public List<String> convertMedications(List<Medication> medications){
        List<String> medicaux = new ArrayList<>();
        for (Medication m : medications){
            medicaux.add(m.getName());
            medicaux.add(m.getDosage());
        }

        return medicaux;
    }

    public List<Allergy> addAllergy(MedicalRecordDto medicalRecordDto) {

        List<String> m = medicalRecordDto.getAllergies();
        List<Allergy> allergies = new ArrayList<>();

        for (String s : m) {
            Optional<Allergy> allergyExist = allergyRepository.findByAllergy(s);
            if(allergyExist.isPresent()){
                allergies.add(allergyExist.get());
            }else {
                Allergy allergy1 = new Allergy();
                allergy1.setName(s);
                allergies.add(allergy1);

                allergyRepository.save(allergy1);
                allergyRepository.flush();
            }

        }
        return allergies;
    }

    private List<Medication> addMedication(MedicalRecordDto medicalRecordDto) {
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
