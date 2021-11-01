package com.openclassroom.projet5.service;

import com.openclassroom.projet5.model.MedicalRecord;
import com.openclassroom.projet5.repository.MedicalRecordRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MedicalRecordService {

    private MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public Iterable<MedicalRecord> list(){
        return medicalRecordRepository.findAll();
    }

    public MedicalRecord save(MedicalRecord medicalRecord){
        return medicalRecordRepository.save(medicalRecord);
    }

    public Iterable<MedicalRecord> save(Collection<MedicalRecord> medicalRecords) {
        return medicalRecordRepository.saveAll(medicalRecords);
    }
}
