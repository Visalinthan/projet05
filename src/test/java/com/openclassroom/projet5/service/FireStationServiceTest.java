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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
        fireStation.setStation(1);

        return fireStation;
    }

    private static FireStationDto getFireStationDto(){
        FireStationDto fireStationDto = new FireStationDto();
        fireStationDto.setId(id);
        fireStationDto.setAddress("10 rue jo");
        fireStationDto.setZip(93330);
        fireStationDto.setCity("lyon");
        fireStationDto.setStation(1);
        return fireStationDto;
    }

    @Test
    public void list() {
        List<FireStation> fireStations = new ArrayList<>();
        fireStations.add(getFireStation());

        when(fireStationRepository.findAll()).thenReturn(fireStations);
        when(fireStationMapper.toDto((FireStation) any())).thenReturn(getFireStationDto());

        assertThat(fireStationService.list().get(0)).isEqualTo(getFireStationDto());

    }

    @Test
    public void save() {
        FireStation fireStation = getFireStation();

        when(fireStationRepository.save(any())).thenReturn(getFireStation());

        assertThat(fireStationService.save(fireStation)).isEqualTo(fireStation);
    }




    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void stationNumberbyAddress() {
    }

    @Test
    public void listAddressByStations() {
    }
}