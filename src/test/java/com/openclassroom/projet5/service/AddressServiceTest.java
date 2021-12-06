package com.openclassroom.projet5.service;

import com.openclassroom.projet5.model.Address;
import com.openclassroom.projet5.model.Person;
import com.openclassroom.projet5.repository.AddressRepository;
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
class AddressServiceTest {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressRepository addressRepository;

    private static Address getAddress(){
        Address address = new Address();
        address.setId(1L);
        address.setAddress("10 rue jo");
        address.setZip(93330);
        address.setCity("lyon");

        return address;
    }


    @Test
    public void list() {
        List<Address> addressList = new ArrayList<>();
        addressList.add(getAddress());

        when(addressRepository.findAll()).thenReturn(addressList);

        assertThat(addressService.list().get(0)).isEqualTo(getAddress());
    }

    @Test
    public void save() {
        Address address = getAddress();

        when(addressRepository.save(any())).thenReturn(getAddress());

        assertThat(addressService.save(address)).isEqualTo(address);

    }
}