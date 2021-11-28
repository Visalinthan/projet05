package com.openclassroom.projet5.service;

import com.openclassroom.projet5.model.Address;
import com.openclassroom.projet5.model.Allergy;
import com.openclassroom.projet5.repository.AllergyRepository;

import java.util.Collection;

public class AllergyService {

    private AllergyRepository allergyRepository;

    public AllergyService(AllergyRepository allergyRepository){
        this.allergyRepository = allergyRepository;
    }

    public Iterable<Allergy> list(){
        return allergyRepository.findAll();
    }

    public Allergy save(Allergy allergy){
        return allergyRepository.save(allergy);
    }

    public Iterable<Allergy> save(Collection<Allergy> allergies) {
        return allergyRepository.saveAll(allergies);
    }

}
