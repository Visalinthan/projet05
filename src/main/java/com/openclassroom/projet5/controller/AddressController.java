package com.openclassroom.projet5.controller;

import com.openclassroom.projet5.model.Address;
import com.openclassroom.projet5.model.Person;
import com.openclassroom.projet5.service.AddressService;
import com.openclassroom.projet5.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tinylog.Logger;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * Récupère la liste des addresses avec la méthode list dans la class addressService
     * @return list d'adresse
     */
    public Iterable<Address> list(){
        Logger.info("Liste adresse !");
        return addressService.list();
    }

}
