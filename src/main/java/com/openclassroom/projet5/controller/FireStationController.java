package com.openclassroom.projet5.controller;

import com.openclassroom.projet5.dto.FireStationDto;
import com.openclassroom.projet5.model.FireStation;
import com.openclassroom.projet5.service.FireStationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class FireStationController {

    private final FireStationService fireStationService;

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @GetMapping("/firestation")
    public ResponseEntity<List<FireStationDto>> getFireStations(){
        List<FireStationDto> fireStations = fireStationService.list();
        return ResponseEntity.ok().body(fireStations);
    }
}
