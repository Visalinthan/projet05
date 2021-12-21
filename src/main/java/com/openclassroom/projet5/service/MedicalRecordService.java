package com.openclassroom.projet5.service;

import com.openclassroom.projet5.dto.MedicalRecordDto;
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
import java.util.Optional;
import java.util.stream.Collectors;

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


    /**
     * Sauvergarde dans la base de données d'un dossier medical la méthode save de l'interface medicalRecordRepository
     * @param medicalRecord
     * @return une sauvegarde d'un dossier medical
     */
    public MedicalRecord save(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }


    /**
     * Sauvergarde dans la base de données de plusieurs dossier medical avec la méthode saveAll de l'interface medicalRecordRepository
     * @param medicalRecords
     * @return une sauvegarde de plusieurs dossier medical
     */
    public Iterable<MedicalRecord> save(Collection<MedicalRecord> medicalRecords) {
        return medicalRecordRepository.saveAll(medicalRecords);
    }

    /**
     * Recherche une liste de dossier medical avec la méthode findAll de l'interface medicalRecordRepository
     * @return une liste de dossier medical
     */
    public List<MedicalRecordDto> list() {
        return medicalRecordRepository.findAll().stream()
                .map(medicalRecordMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Sauvergarde ou modifaication dans la base de données un dossier medical d'une personne en fonction de son nom et prénom et son dossier médical éxistant
     * @param firstName
     * @param lastName
     * @param medicalRecordDto
     * @return
     */
    public MedicalRecordDto save(String firstName, String lastName, MedicalRecordDto medicalRecordDto) {
        medicalRecordDto.setFirstName(firstName);
        medicalRecordDto.setLastName(lastName);
        MedicalRecord medicalRecord = medicalRecordMapper.toEntity(medicalRecordDto);
        medicalRecord = medicalRecordRepository.save(medicalRecord);
        return medicalRecordMapper.toDto(medicalRecord);

    }

    /**
     * Suppression dans la base de données d'un dossier médical en fonction du nom et du prénom entrée dans le paramètre
     * @param firstName
     * @param lastName
     */
    public void deleteByNames(String firstName, String lastName) {
        medicalRecordRepository.deleteByNames(firstName, lastName);
    }

    /**
     * Recherche un dossier médical d'une personne en fonction du nom et du prénom entrée dans le paramètre
     * @param firstName
     * @param lastName
     * @return un dossier médical
     */
    public Optional<MedicalRecordDto> listMedicalByNames(String firstName, String lastName) {
        Optional<MedicalRecordDto> medicalRecordDto = medicalRecordRepository.findByPerson(firstName, lastName).stream()
                .map(m->medicalRecordMapper.toDto(m))
                .findFirst();

        return medicalRecordDto;

    }

}
