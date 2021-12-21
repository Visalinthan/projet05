package com.openclassroom.projet5.service;

import com.openclassroom.projet5.dto.FireStationDto;
import com.openclassroom.projet5.mapper.FireStationMapper;
import com.openclassroom.projet5.model.Address;
import com.openclassroom.projet5.model.FireStation;
import com.openclassroom.projet5.repository.AddressRepository;
import com.openclassroom.projet5.repository.FireStationRepository;
import com.openclassroom.projet5.repository.PersonRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Component
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

    /**
     * Sauvergarde dans la base de données d'une caserne de pompier avec la méthode save de l'interface fireStationRepository
     * @param fireStations
     * @return Sauvergarde d'une caserne de pompier
     */
    public FireStation save(FireStation fireStations){
        return fireStationRepository.save(fireStations);
    }

    /**
     * Sauvergarde dans la base de données des casernes de pompiers avec la méthode saveAll de l'interface fireStationRepository
     * @param fireStations
     * @return Sauvergarde des casernes de pompier
     */
    public  Iterable<FireStation> save(Collection<FireStation> fireStations){
        return fireStationRepository.saveAll(fireStations);
    }

    /**
     * Recherche dans la base de données des casernes de pompiers avec la méthode findAll de l'interface fireStationRepository
     * @return
     */
    public List<FireStationDto> list(){
        return fireStationRepository.findAll().stream()
                .map(fireStationMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Sauvergarde dans la base de données d'une caserne de pompier DTO avec la méthode save de l'interface fireStationRepository
     * @param fireStationDto
     * @return Sauvergarde d'une caserne de pompier DTO
     */
    public FireStationDto save(FireStationDto fireStationDto){
        FireStation fireStation = fireStationMapper.toEntity(fireStationDto);
        fireStation = fireStationRepository.save(fireStation);
        return fireStationMapper.toDto(fireStation);
    }

    /**
     * Modification dans la base de données d'une caserne de pompier DTO en récupérant l'id dans le paramètre
     * @param id
     * @param fireStationDto
     * @return une caserne de pompier DTO modifié
     */
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

    /**
     * Suppression dans la base de données d'une caserne en récupérant l'id dans le paramètre
     * @param id
     */
    public void delete(Long id){
        fireStationRepository.deleteById(id);
    }

    /**
     * Récupère le numéro de caserne sur l'adresse indiqué dans le paramètre
     * @param address
     * @return un numéro caserne
     */
    public int stationNumberByAddress(String address){
        int station = fireStationRepository.findFireStationByAddress(address);
        int s = station;
        return s;
    }



}
