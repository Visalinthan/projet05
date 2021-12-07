package com.openclassroom.projet5.service;

import com.openclassroom.projet5.dto.MedicalRecordDto;
import com.openclassroom.projet5.mapper.MedicalRecordMapper;
import com.openclassroom.projet5.model.*;
import com.openclassroom.projet5.repository.MedicalRecordRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @Mock
    private MedicalRecordMapper medicalRecordMapper;

    private static Long id = 1L;
    private static LocalDate birthDate = LocalDate.of(1995, 1, 8);

    private static Person getPerson(){
        Address address = new Address();
        Person person = new Person();
        person.setId(id);
        person.setFirstName("TOM");
        person.setLastName("Danny");
        person.setEmail("tom@gmail.com");
        person.setPhone("0125639865");
        person.setBirthdate(birthDate);
        address.setId(1L);
        address.setAddress("10 rue jo");
        address.setZip(93330);
        address.setCity("lyon");
        person.setAddress(address);

        return person;

    }

    private static List<Medication> getMedication(){
        List<Medication> medications = new ArrayList<>();
        Medication medication = new Medication();
        medication.setId(id);
        medication.setName("doliprane");
        medication.setDosage("100mg");

        medications.add(medication);
        return medications;
    }
    private static List<Allergy> getAllergy(){
        List<Allergy> allergies = new ArrayList<>();
        Allergy allergy = new Allergy();
        allergy.setId(id);
        allergy.setName("pollen");
        allergies.add(allergy);
        return allergies;
    }

    private static MedicalRecord getMedicalRecord(){
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId(id);
        medicalRecord.setMedications(getMedication());
        medicalRecord.setAllergies(getAllergy());
        medicalRecord.setPerson(getPerson());
        return medicalRecord;
    }

    private static  MedicalRecordDto getMedicalRecordDto(){
        MedicalRecordDto medicalRecordDto = new MedicalRecordDto();
        List<String> medications = new ArrayList<>();
        List<String> allergies = new ArrayList<>();
        medicalRecordDto.setFirstName("TOM");
        medicalRecordDto.setLastName("Danny");
        medicalRecordDto.setBirthDate("1995-01-08");
        medications.add("doliprane:100mg");
        allergies.add("pollen");
        medicalRecordDto.setMedications(medications);
        medicalRecordDto.setAllergies(allergies);

        return medicalRecordDto;
    }

    @Test
    public void checkSaveMedicalRecord() {
        MedicalRecordDto medicalRecordDto = getMedicalRecordDto();

        when(medicalRecordMapper.toEntity((MedicalRecordDto) any())).thenReturn(getMedicalRecord());
        when(medicalRecordRepository.save(any())).thenReturn(getMedicalRecord());
        when(medicalRecordMapper.toDto((MedicalRecord) any())).thenReturn(getMedicalRecordDto());

        assertThat(medicalRecordService.save(medicalRecordDto.getFirstName(),medicalRecordDto.getLastName(),medicalRecordDto)).isEqualTo(medicalRecordDto);
    }

    @Test
    public void checkFirstMedicalRecordsInList() {
        List<MedicalRecord> medicalRecord =new ArrayList<>();
        medicalRecord.add(getMedicalRecord());

        when(medicalRecordRepository.findAll()).thenReturn(medicalRecord);
        when(medicalRecordMapper.toDto((MedicalRecord) any())).thenReturn(getMedicalRecordDto());

        assertThat(medicalRecordService.list().get(0)).isEqualTo(getMedicalRecordDto());
    }

    @Test
    public void verifyDeleteByNames() {
        MedicalRecordDto medicalRecordDto = getMedicalRecordDto();
        doNothing().when(medicalRecordRepository).deleteByNames(medicalRecordDto.getFirstName(),medicalRecordDto.getLastName());

        medicalRecordService.deleteByNames(medicalRecordDto.getFirstName(),medicalRecordDto.getLastName());

        verify(medicalRecordRepository, times(1)).deleteByNames(medicalRecordDto.getFirstName(),medicalRecordDto.getLastName());
    }

    @Test
    public void checkListMedicalByNames() {
        List<MedicalRecordDto> medicalRecordDtos =new ArrayList<>();
        medicalRecordDtos.add(getMedicalRecordDto());

        when(medicalRecordRepository.findByPerson(anyString(),anyString())).thenReturn(Optional.of(getMedicalRecord()));
        when(medicalRecordMapper.toDto((MedicalRecord) any())).thenReturn(getMedicalRecordDto());

        assertThat(medicalRecordService.listMedicalByNames(medicalRecordDtos.get(0).getFirstName(),medicalRecordDtos.get(0).getLastName())).isEqualTo(Optional.of(getMedicalRecordDto()));
    }
}