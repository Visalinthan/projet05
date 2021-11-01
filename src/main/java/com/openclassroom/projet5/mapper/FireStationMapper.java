package com.openclassroom.projet5.mapper;

import com.openclassroom.projet5.dto.FireStationDto;
import com.openclassroom.projet5.model.Address;
import com.openclassroom.projet5.model.FireStation;
import com.openclassroom.projet5.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FireStationMapper {

    @Autowired
    private AddressRepository addressRepository;

    public FireStation toEntity(FireStationDto fireStationDto) {
        FireStation fireStation = new FireStation();

        Optional<Address> address = addressRepository.findByAddress(fireStationDto.getAddress());

        address.ifPresent(fireStation::setAddress);

        fireStation.setStation(fireStationDto.getStation());
        return fireStation;
    }

}



