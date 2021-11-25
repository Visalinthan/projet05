package com.openclassroom.projet5.controller;

import com.openclassroom.projet5.dto.MedicalRecordDto;
import com.openclassroom.projet5.dto.PersonDto;
import com.openclassroom.projet5.service.MedicalRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }
/*
    @GetMapping("/medicalRecords")
    public ResponseEntity<List<MedicalRecordDto>> getMedicalRecords() {
        List<MedicalRecordDto> medicalRecordDto = medicalRecordService.list();
        return ResponseEntity.ok().body(medicalRecordDto);
    }

    @PostMapping("/medicalRecord/{id}")
    public ResponseEntity<MedicalRecordDto> createMedicalRecord(@PathVariable("id") long id,@RequestBody MedicalRecordDto medicalRecordDto){

        MedicalRecordDto result = medicalRecordService.save(id,medicalRecordDto);

        return ResponseEntity.ok().body(result);
    }
*/
}
