package com.openclassroom.projet5.service;

import com.openclassroom.projet5.dto.FireStationDto;
import com.openclassroom.projet5.dto.PersonDto;
import com.openclassroom.projet5.mapper.FireStationMapper;
import com.openclassroom.projet5.model.FireStation;
import com.openclassroom.projet5.repository.FireStationRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FireStationService {

    private FireStationRepository fireStationRepository;
    private final FireStationMapper fireStationMapper;

    public FireStationService(FireStationRepository fireStationRepository, FireStationMapper fireStationMapper){
        this.fireStationRepository = fireStationRepository;
        this.fireStationMapper = fireStationMapper;
    }
    public List<FireStationDto> list(){
         List<FireStation> fireStations = fireStationRepository.findAll();
        return fireStations.stream()
                .map(fireStationMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

   /* public Iterable<FireStation> list(){
        return fireStationRepository.findAll();
    }*/

    public FireStation save(FireStation fireStations){
        return fireStationRepository.save(fireStations);
    }

    public  Iterable<FireStation> save(Collection<FireStation> fireStations){
        return fireStationRepository.saveAll(fireStations);
    }
}
