package com.openclassroom.projet5.service;

import com.openclassroom.projet5.dto.FireStationDto;
import com.openclassroom.projet5.mapper.FireStationMapper;
import com.openclassroom.projet5.model.Address;
import com.openclassroom.projet5.model.FireStation;
import com.openclassroom.projet5.model.Person;
import com.openclassroom.projet5.repository.AddressRepository;
import com.openclassroom.projet5.repository.FireStationRepository;
import com.openclassroom.projet5.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FireStationService {

    private FireStationRepository fireStationRepository;
    private PersonRepository personRepository;
    private AddressRepository addressRepository;
    private final FireStationMapper fireStationMapper;

    public FireStationService(FireStationRepository fireStationRepository, FireStationMapper fireStationMapper, PersonRepository personRepository,AddressRepository addressRepository){
        this.fireStationRepository = fireStationRepository;
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
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
/*
    public List<Person>  listPersonByStationNumber(int StationNumber){
        List<Person> persons = null;
        List<Address> AddressOfStation = fireStationRepository.findByStation(StationNumber); ;
        List<Person> AllPerson = personRepository.findAll();
        for (Address a : AddressOfStation) {
            persons = personRepository.findByAddress(a);
        }

        return persons;
    }
*/

}
