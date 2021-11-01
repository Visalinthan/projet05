package com.openclassroom.projet5.service;

import com.openclassroom.projet5.model.Address;
import com.openclassroom.projet5.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AddressService {

    private AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Iterable<Address> list(){
        return addressRepository.findAll();
    }

    public Address save(Address address){
        return addressRepository.save(address);
    }

    public Iterable<Address> save(Collection<Address> addresses) {
        return addressRepository.saveAll(addresses);
    }
}
