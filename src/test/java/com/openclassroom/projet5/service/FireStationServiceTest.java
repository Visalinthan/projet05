package com.openclassroom.projet5.service;

import com.openclassroom.projet5.dto.FireStationDto;
import com.openclassroom.projet5.mapper.FireStationMapper;
import com.openclassroom.projet5.model.Address;
import com.openclassroom.projet5.model.FireStation;
import com.openclassroom.projet5.repository.FireStationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FireStationServiceTest {

    @InjectMocks
    private FireStationService fireStationService;

    @Mock
    private FireStationRepository fireStationRepository;

    @Mock
    private FireStationMapper fireStationMapper;

    private static Long id = 1L;

    private static FireStation getFireStation(){
        Address address = new Address();
        FireStation fireStation = new FireStation();

        fireStation.setId(id);
        address.setId(id);
        address.setAddress("10 rue jo");
        address.setZip(93330);
        address.setCity("lyon");
        fireStation.setAddress(address);
        fireStation.setStation(6);

        return fireStation;
    }

    private static FireStationDto getFireStationDto(){
        FireStationDto fireStationDto = new FireStationDto();
        fireStationDto.setId(id);
        fireStationDto.setAddress("10 rue jo");
        fireStationDto.setZip(93330);
        fireStationDto.setCity("lyon");
        fireStationDto.setStation(6);
        return fireStationDto;
    }

    @Test
    public void list() {
        List<FireStation> fireStations = new ArrayList<>();
        fireStations.add(getFireStation());

        when(fireStationRepository.findAll()).thenReturn(fireStations);
        when(fireStationMapper.toDto((FireStation) any())).thenReturn(getFireStationDto());

        FireStationDto fireStationDto = getFireStationDto() ;
        assertThat(fireStationService.list().get(0).getId()).isEqualTo(getFireStationDto().getId());

    }

    @Test
    public void save() {
        FireStation fireStation = getFireStation();

        when(fireStationRepository.save(any())).thenReturn(getFireStation());

        assertThat(fireStationService.save(fireStation)).isEqualTo(fireStation);
    }


    @Test
    public void update() {
        FireStationDto fireStationDto = getFireStationDto();

        when(fireStationRepository.findById(fireStationDto.getId())).thenReturn(Optional.of(getFireStation()));
        when(fireStationMapper.toDto((FireStation) any())).thenReturn(getFireStationDto());

        assertThat(fireStationService.update(fireStationDto.getId(),fireStationDto).getId()).isEqualTo(fireStationDto.getId());
    }

    @Test
    public void delete() {
        FireStation fireStation = getFireStation();

        fireStationService.delete(fireStation.getId());

        verify(fireStationRepository, times(1)).deleteById(fireStation.getId());
    }

    @Test
    public void stationNumberByAddress() {
        FireStation fireStation = getFireStation();
        int station;
        String address;

        when(fireStationRepository.findFireStationByAddress(anyString())).thenReturn(fireStation.getStation());
        address = fireStation.getAddress().getAddress();
        station = fireStationRepository.findFireStationByAddress(address);

        assertThat(fireStationService.stationNumberByAddress(address)).isEqualTo("N° station :"+ station);
    }

    @Test
    public void listAddressByStations() {

    }
}