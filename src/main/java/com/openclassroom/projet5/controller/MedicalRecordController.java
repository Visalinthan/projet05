package com.openclassroom.projet5.controller;

import com.openclassroom.projet5.dto.MedicalRecordDto;
import com.openclassroom.projet5.dto.PersonDto;
import com.openclassroom.projet5.service.MedicalRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;

import java.util.List;

@RestController
@RequestMapping
public class  MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    /**
     * Récupère la liste des dossier medicaux avec la méthode list dans la class medicalRecordService
     * @return une réponse http avec la liste de dossier médicaux
     */
    @GetMapping("/medicalRecord")
    public ResponseEntity<List<MedicalRecordDto>> getMedicalRecords() {
        List<MedicalRecordDto> medicalRecordDto = medicalRecordService.list();

        Logger.info("Medical record listé !");

        return ResponseEntity.ok().body(medicalRecordDto);
    }

    /**
     * Enregitsre un dossier médical avec la méthode save dans la class medicalRecordService
     * @param firstName
     * @param lastName
     * @param medicalRecordDto
     * @return une réponse http avec un dossier medical enregistré
     */
    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordDto> createMedicalRecord(@RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName,@RequestBody MedicalRecordDto medicalRecordDto){

        MedicalRecordDto result = medicalRecordService.save(firstName,lastName,medicalRecordDto);

        Logger.info("Le dossier médical  de "+firstName+" "+lastName+" a été ajouté !");

        return ResponseEntity.ok().body(result);
    }

    /**
     * Modifie une caserne de pompier avec la méthode save dans la class medicalRecordService
     * @param firstName
     * @param lastName
     * @param medicalRecordDto
     * @return une réponse http avec le dossier medical modifié
     */
    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordDto> updateMedicalRecord(@RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName,@RequestBody MedicalRecordDto medicalRecordDto){

        MedicalRecordDto result = medicalRecordService.save(firstName,lastName,medicalRecordDto);

        Logger.info("Le dossier médical  de "+firstName+" "+lastName+" a été modifié !");

        return ResponseEntity.ok().body(result);
    }

    /**
     * Supprime une personne avec la méthode deleteByNames dans la class medicalRecordService
     * @param firstName
     * @param lastName
     * @return une réponse http avec le dossier medical supprimé
     */
    @DeleteMapping("/medicalRecord")
    public ResponseEntity<?> deletePersonByNames(@RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName) {
        medicalRecordService.deleteByNames(firstName,lastName);

        Logger.warn("Le dossier médical de "+firstName+" "+lastName+" a été supprimé !");

        return ResponseEntity.ok().build();
    }

}
