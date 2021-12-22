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

    /**
     * Recherche toute la liste d'adresse dans la base de données
     * @return liste d'adresse
     */
    public List<Address> list(){
        return addressRepository.findAll();
    }

    /**
     * Sauvergarde dans la base de données une adresse avec la méthode save de l'interface addressRepository
     * @param address
     * @return Sauvegarde d'une adresse
     */
    public Address save(Address address){
        return addressRepository.save(address);
    }

    /**
     * Sauvergarde dans la base de données des adresses avec la méthode saveAll de l'interface addressRepository
     * @param addresses
     * @return Sauvegarde une liste d'adresse
     */
    public Iterable<Address> save(Collection<Address> addresses) {
        return addressRepository.saveAll(addresses);
    }

    /**
     * Recherche dans la base de données des adresses couvert par les numéros de caserne entré dans les paramètres
     * @param station
     * @return liste d'addresse
     */
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
