package com.openclassroom.projet5.service;

import com.openclassroom.projet5.model.Address;
import com.openclassroom.projet5.repository.AddressRepository;
import com.openclassroom.projet5.repository.FireStationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AddressService {

    private FireStationRepository fireStationRepository;
    private AddressRepository addressRepository;

    public AddressService(FireStationRepository fireStationRepository, AddressRepository addressRepository) {
        this.fireStationRepository = fireStationRepository;
        this.addressRepository = addressRepository;
    }

    public List<Address> list(){
        return addressRepository.findAll();
    }

    public Address save(Address address){
        return addressRepository.save(address);
    }

    public Iterable<Address> save(Collection<Address> addresses) {
        return addressRepository.saveAll(addresses);
    }

    public List<Address> listAddressByStations(List<Integer>station){
        List<Address> addresses = new ArrayList<>();
        for (int i : station){
            List<Address> addressesFind = fireStationRepository.findAddressByStation(i);
            for (Address a :addressesFind) {
                addresses.add(a);
            }
        }

        return addresses;
    }
}
