package com.openclassroom.projet5.service;

import com.openclassroom.projet5.model.Address;
import com.openclassroom.projet5.model.Person;
import com.openclassroom.projet5.repository.AddressRepository;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    FireStationRepository fireStationRepository;

    private static Address getAddress(){
        Address address = new Address();
        address.setId(1L);
        address.setAddress("10 rue jo");
        address.setZip(93330);
        address.setCity("lyon");

        return address;
    }


    @Test
    public void checkFirstAddressInList() {
        List<Address> addressList = new ArrayList<>();
        addressList.add(getAddress());

        when(addressRepository.findAll()).thenReturn(addressList);

        assertThat(addressService.list().get(0)).isEqualTo(getAddress());
    }

    @Test
    public void checkSaveAddress() {
        Address address = getAddress();

        when(addressRepository.save(any())).thenReturn(getAddress());

        assertThat(addressService.save(address)).isEqualTo(address);
    }

    @Test
    public void checkListAddressByStations(){
        List<Address> addresses = new ArrayList<>();
        List<Integer> stations = new ArrayList<>();
        addresses.add(getAddress());
        stations.add(1);

        when(fireStationRepository.findAddressByStation(anyInt())).thenReturn(addresses);

        assertThat(addressService.listAddressByStations(stations)).isEqualTo(addresses);

    }


}