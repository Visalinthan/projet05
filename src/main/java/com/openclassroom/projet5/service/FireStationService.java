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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FireStationService {

    private FireStationRepository fireStationRepository;
    private final FireStationMapper fireStationMapper;

    public FireStationService(FireStationRepository fireStationRepository, FireStationMapper fireStationMapper){
        this.fireStationRepository = fireStationRepository;
        this.fireStationMapper = fireStationMapper;
    }

    public FireStation save(FireStation fireStations){
        return fireStationRepository.save(fireStations);
    }

    public  Iterable<FireStation> save(Collection<FireStation> fireStations){
        return fireStationRepository.saveAll(fireStations);
    }

    public Iterable<FireStation> listFireStation(){
        return fireStationRepository.findAll();
    }

    public List<FireStationDto> list(){
         List<FireStation> fireStations = fireStationRepository.findAll();
        return fireStations.stream()
                .map(fireStationMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public FireStationDto save(FireStationDto fireStationDto){
        FireStation fireStation = fireStationMapper.toEntity(fireStationDto);
        fireStation = fireStationRepository.save(fireStation);
        return fireStationMapper.toDto(fireStation);
    }

    public FireStationDto update(Long id, FireStationDto fireStationDto){
        Optional<FireStationDto> fireStationDtoFind = fireStationRepository.findById(id).stream()
                .map(fireStationMapper::toDto)
                .findFirst();

        FireStationDto updatedFireStation = null;
        if (fireStationDtoFind.isPresent()){
            FireStationDto fireStationDtoUpdate = fireStationDtoFind.get();
            fireStationDtoUpdate.setStation(fireStationDto.getStation());
            fireStationDtoUpdate.setAddress(fireStationDto.getAddress());
            fireStationDtoUpdate.setZip(fireStationDto.getZip());
            fireStationDtoUpdate.setCity(fireStationDto.getCity());
            FireStation fireStation = fireStationMapper.toEntity(fireStationDtoUpdate);
            fireStation = fireStationRepository.save(fireStation);
            updatedFireStation = fireStationMapper.toDto(fireStation);
        }

        return updatedFireStation;
    }

    public void delete(Long id){
        fireStationRepository.deleteById(id);
    }


}
