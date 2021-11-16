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


    private final AddressRepository addressRepository;

    public FireStationMapper(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public FireStationDto toDto(FireStation fireStation) {
        FireStationDto fireStationDto = new FireStationDto();
        fireStationDto.setId(fireStation.getId());
        fireStationDto.setStation(fireStation.getStation());
        final Address address = fireStation.getAddress();
        if(address != null && address.getAddress() != null) {
            fireStationDto.setAddress(address.getAddress());
            fireStationDto.setCity(address.getCity());
            fireStationDto.setZip(address.getZip());
        }

        return fireStationDto;
    }

    public FireStation toEntity(FireStationDto fireStationDto) {
        FireStation fireStation = new FireStation();
        fireStation.setId(fireStationDto.getId());
        Optional<Address> addressExist = addressRepository.findByAddress(fireStationDto.getAddress().trim());

        addressExist.ifPresentOrElse(fireStation::setAddress, () -> {
            Address address = new Address();
            address.setAddress(fireStationDto.getAddress().trim());
            address.setCity(fireStationDto.getCity());
            address.setZip(fireStationDto.getZip());

            address = addressRepository.save(address);
            addressRepository.flush();
            fireStation.setAddress(address);
        });

        fireStation.setStation(fireStationDto.getStation());
        return fireStation;
    }


}



