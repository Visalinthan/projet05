package com.openclassroom.projet5.service;

import com.openclassroom.projet5.model.FireStation;
import com.openclassroom.projet5.repository.FireStationRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FireStationService {

    private FireStationRepository fireStationRepository;

    public FireStationService(FireStationRepository fireStationRepository){
        this.fireStationRepository = fireStationRepository;
    }

    public Iterable<FireStation> list(){
        return fireStationRepository.findAll();
    }

    public FireStation save(FireStation fireStations){
        return fireStationRepository.save(fireStations);
    }

    public  Iterable<FireStation> save(Collection<FireStation> fireStations){
        return fireStationRepository.saveAll(fireStations);
    }
}
