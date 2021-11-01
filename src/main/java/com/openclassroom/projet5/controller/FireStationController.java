package com.openclassroom.projet5.controller;

import com.openclassroom.projet5.model.FireStation;
import com.openclassroom.projet5.service.FireStationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/firestations")
public class FireStationController {
    private final FireStationService fireStationService;

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @GetMapping("/list")
    public Iterable<FireStation> list(){
        return fireStationService.list();
    }
}
