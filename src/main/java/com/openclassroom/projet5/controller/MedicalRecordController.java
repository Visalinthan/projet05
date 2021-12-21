package com.openclassroom.projet5.controller;

import com.openclassroom.projet5.dto.MedicalRecordDto;
import com.openclassroom.projet5.dto.PersonDto;
import com.openclassroom.projet5.service.MedicalRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class  MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping("/medicalRecord")
    public ResponseEntity<List<MedicalRecordDto>> getMedicalRecords() {
        List<MedicalRecordDto> medicalRecordDto = medicalRecordService.list();
        return ResponseEntity.ok().body(medicalRecordDto);
    }

    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordDto> createMedicalRecord(@RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName,@RequestBody MedicalRecordDto medicalRecordDto){

        MedicalRecordDto result = medicalRecordService.save(firstName,lastName,medicalRecordDto);

        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordDto> updateMedicalRecord(@RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName,@RequestBody MedicalRecordDto medicalRecordDto){

        MedicalRecordDto result = medicalRecordService.save(firstName,lastName,medicalRecordDto);

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity<?> deletePersonByNames(@RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName) {
        medicalRecordService.deleteByNames(firstName,lastName);
        return ResponseEntity.ok().build();
    }

}
