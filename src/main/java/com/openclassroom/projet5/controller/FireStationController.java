package com.openclassroom.projet5.controller;

import com.openclassroom.projet5.dto.FireStationDto;
import com.openclassroom.projet5.dto.PersonDto;
import com.openclassroom.projet5.model.Address;
import com.openclassroom.projet5.model.FireStation;
import com.openclassroom.projet5.model.Person;
import com.openclassroom.projet5.repository.FireStationRepository;
import com.openclassroom.projet5.service.FireStationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class FireStationController {

    private final FireStationService fireStationService;
    private FireStationRepository fireStationRepository;

    public FireStationController(FireStationService fireStationService, FireStationRepository fireStationRepository) {
        this.fireStationService = fireStationService;
        this.fireStationRepository = fireStationRepository;
    }

    /**
     * Récupère la liste des casernes avec la méthode list dans la class fireStationService
     * @return une réponse http avec la liste de caserne de pompier
     */
    @GetMapping("/firestations")
    public ResponseEntity<List<FireStationDto>> getFireStations(){
        List<FireStationDto> fireStations = fireStationService.list();

        Logger.info("Caserne de pompier listé !");

        return ResponseEntity.ok().body(fireStations);
    }

    /**
     * Enregitsre une caserne de pompier avec la méthode save dans la class fireStationService
     * @param fireStationDto
     * @return une réponse http avec la caserne de pompier enregistré
     * @throws Exception
     */
    @PostMapping("/firestation")
    public ResponseEntity<FireStationDto> createFireStation(@RequestBody FireStationDto fireStationDto) throws  Exception{
        if(fireStationDto.getId() != null){
            throw new Exception("erreur ");
        }

        FireStationDto result = fireStationService.save(fireStationDto);

        Logger.info("Caserne de pompier enregistré !");

        return ResponseEntity.ok().body(result);
    }

    /**
     * Modifie une caserne de pompier avec la méthode update dans la class fireStationService
     * @param id
     * @param fireStationDto
     * @return  une réponse http avec la caserne de pompier modifié
     * @throws Exception
     */
    @PutMapping("/firestation/{id}")
    public ResponseEntity<FireStationDto> updateFireStation(@PathVariable("id") long id,@RequestBody FireStationDto fireStationDto) throws  Exception{

        FireStationDto result = fireStationService.update(id,fireStationDto);

        Logger.info("La caserne avec l'id n° "+id +" a été modifié !");

        return ResponseEntity.ok().body(result);
    }

    /**
     * Supprime une caserne avec la méthode delete dans la class fireStationService
     * @param id
     * @return une réponse http avec la caserne de pompier supprimé
     */
    @DeleteMapping("/firestation/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        fireStationService.delete(id);

        Logger.warn("La caserne avec l'id n° "+id +" a été supprimé !");

        return ResponseEntity.ok().build();
    }

}
