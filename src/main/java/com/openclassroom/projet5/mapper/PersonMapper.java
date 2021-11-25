package com.openclassroom.projet5.mapper;

import com.openclassroom.projet5.dto.MedicalRecordDto;
import com.openclassroom.projet5.dto.PersonDto;
import com.openclassroom.projet5.model.*;
import com.openclassroom.projet5.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class PersonMapper {

    private final AddressRepository addressRepository;

    public PersonMapper(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public PersonDto toDto(Person person) {
        PersonDto personDto = new PersonDto();
        personDto.setId(person.getId());
        personDto.setFirstName(person.getFirstName());
        personDto.setLastName(person.getLastName());
        personDto.setEmail(person.getEmail());
        personDto.setPhone(person.getPhone());
        personDto.setBirthdate(person.getBirthdate());
        final Address address = person.getAddress();
        if(address != null && address.getAddress() != null) {
            personDto.setAddress(address.getAddress());
            personDto.setCity(address.getCity());
            personDto.setZip(address.getZip());
        }

        return personDto;
    }


/*
    public Person toEntity(PersonDto personDto, List<MedicalRecordDto> medicalRecordDto) {
        Person person = new Person();
        MedicalRecord medicalRecord = new MedicalRecord();

        person.setId(personDto.getId());
        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setEmail(personDto.getEmail());
        person.setPhone(personDto.getPhone());

        Optional<Address> addressExist = addressRepository.findByAddress(personDto.getAddress().trim());


        addressExist.ifPresentOrElse(person::setAddress, () -> {
            Address address = new Address();
            address.setAddress(personDto.getAddress().trim());
            address.setCity(personDto.getCity());
            address.setZip(personDto.getZip());

            address = addressRepository.save(address);
            addressRepository.flush();
            person.setAddress(address);
        });

        LocalDate birthDate = this.findBirthDateByFirstNameAndLastName(personDto.getFirstName(), personDto.getLastName(), medicalRecordDto);
        person.setBirthdate(birthDate);

        List<Medication> medications = this.findMedicationByFirstNameAndLastName(personDto.getFirstName(), personDto.getLastName(), medicalRecordDto);
        person.setMedications(medications);

        List<Allergy> allergies = this.findAllergyByFirstNameAndLastName(personDto.getFirstName(), personDto.getLastName(), medicalRecordDto);
        person.setAllergys(allergies);

        medicalRecord.setMedications(medications);
        medicalRecord.setAllergies(allergies);

        return person;
    }
*/
    public Person toEntity(PersonDto personDto) {
        Person person = new Person();
        MedicalRecord medicalRecord = new MedicalRecord();

        person.setId(personDto.getId());
        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setEmail(personDto.getEmail());
        person.setPhone(personDto.getPhone());

        Optional<Address> addressExist = addressRepository.findByAddress(personDto.getAddress().trim());


        addressExist.ifPresentOrElse(person::setAddress, () -> {
            Address address = new Address();
            address.setAddress(personDto.getAddress().trim());
            address.setCity(personDto.getCity());
            address.setZip(personDto.getZip());

            address = addressRepository.save(address);
            addressRepository.flush();
            person.setAddress(address);
        });

        return person;
    }

    private LocalDate findBirthDateByFirstNameAndLastName(String firstName, String lastName, List<MedicalRecordDto> medicalRecordDto) {
        String s = medicalRecordDto.stream()
                .filter(m -> m.getFirstName().equals(firstName) && m.getLastName().equals(lastName))
                .findFirst()
                .map(MedicalRecordDto::getBirthDate)
                .orElse(null);

        if (s == null) {
            return null;
        }

        try {
            DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("MM/dd/yyyy");
            return LocalDate.parse(s,formatter);
        } catch (Exception e) {
            return  null;
        }

    }

/*
    private List<Allergy> findAllergyByFirstNameAndLastName(String firstName, String lastName, List<MedicalRecordDto> medicalRecordDto) {
        List<String> s = medicalRecordDto.stream()
                .filter(m -> m.getFirstName().equals(firstName) && m.getLastName().equals(lastName))
                .findFirst()
                .map(MedicalRecordDto::getAllergies)
                .orElse(null);
        List<Allergy> allergies = new ArrayList<>();
        for (String allergy : s) {
            Allergy allergy1 = new Allergy();
            allergy1.setName(allergy);
            allergies.add(allergy1);
        }
        return allergies;
    }

    private List<Medication> findMedicationByFirstNameAndLastName(String firstName, String lastName, List<MedicalRecordDto> medicalRecordDto) {
        List<String> s = medicalRecordDto.stream()
                .filter(m -> m.getFirstName().equals(firstName) && m.getLastName().equals(lastName))
                .findFirst()
                .map(MedicalRecordDto::getMedications)
                .orElse(null);
        List<Medication> medications = new ArrayList<>();
        for (String medication : s) {
            Medication medication1 = new Medication();
            String[] m = medication.split(":", 0);
            medication1.setName(m[0]);
            medication1.setDosage(m[1]);

            medications.add(medication1);
        }
        return medications;
    }

*/

}
