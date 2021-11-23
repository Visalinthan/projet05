package com.openclassroom.projet5.service;

import com.openclassroom.projet5.dto.MedicalRecordDto;
import com.openclassroom.projet5.dto.PersonDto;
import com.openclassroom.projet5.mapper.MedicalRecordMapper;
import com.openclassroom.projet5.mapper.PersonMapper;
import com.openclassroom.projet5.model.MedicalRecord;
import com.openclassroom.projet5.model.Person;
import com.openclassroom.projet5.repository.MedicalRecordRepository;
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

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository, PersonMapper personMapper, MedicalRecordMapper medicalRecordMapper, PersonRepository personRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.personMapper = personMapper;
        this.medicalRecordMapper = medicalRecordMapper;
        this.personRepository = personRepository;
    }

    /*public Iterable<MedicalRecord> list(){
        return medicalRecordRepository.findAll();
    }*/

    public MedicalRecord save(MedicalRecord medicalRecord){
        return medicalRecordRepository.save(medicalRecord);
    }

    public Iterable<MedicalRecord> save(Collection<MedicalRecord> medicalRecords) {
        return medicalRecordRepository.saveAll(medicalRecords);
    }

    public List<MedicalRecordDto> list(){
        return personRepository.findAll().stream()
                .map(medicalRecordMapper::toDto)
                .collect(Collectors.toList());
    }
/*
   public MedicalRecordDto save(Long personId, List<MedicalRecordDto> medicalRecordDto){
        Person person = personRepository.findById(personId).stream().findFirst().get();
        PersonDto personDto = personMapper.toDto(person);
        personMapper.toEntity(personDto,medicalRecordDto);
        MedicalRecord medicalRecord = medicalRecordRepository.save(medicalRecord);
        return medicalRecordMapper.toDto(medicalRecord);
    }
*/
}
