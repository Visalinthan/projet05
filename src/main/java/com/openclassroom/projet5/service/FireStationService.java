package com.openclassroom.projet5.service;

import com.openclassroom.projet5.dto.FireStationDto;
import com.openclassroom.projet5.dto.PersonDto;
import com.openclassroom.projet5.mapper.FireStationMapper;
import com.openclassroom.projet5.model.FireStation;
import com.openclassroom.projet5.model.Person;
import com.openclassroom.projet5.repository.FireStationRepository;
import com.openclassroom.projet5.repository.PersonRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FireStationService {

    private FireStationRepository fireStationRepository;
    private PersonRepository personRepository;
    private final FireStationMapper fireStationMapper;

    public FireStationService(FireStationRepository fireStationRepository, FireStationMapper fireStationMapper, PersonRepository personRepository){
        this.fireStationRepository = fireStationRepository;
        this.personRepository = personRepository;
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

    @Transactional
    public List<PersonDto> listPersonByStationNumber(int StationNumber){
        Optional<FireStationDto> fireStationDto = fireStationRepository.findByStation(StationNumber).stream()
                .map(fireStationMapper::toDto)
                .findFirst();
        List<PersonDto> personDto = null;
        if (fireStationDto.isPresent()){
           FireStationDto fireStationDto1 = fireStationDto.get();
           personDto = personRepository.findByAddress(fireStationDto1.getAddress());
        }

        return personDto;
    }


}
