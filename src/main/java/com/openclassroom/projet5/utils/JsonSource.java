package com.openclassroom.projet5.utils;

import com.openclassroom.projet5.dto.FireStationDto;
import com.openclassroom.projet5.dto.MedicalRecordDto;
import com.openclassroom.projet5.dto.PersonDto;

import java.util.List;

public class JsonSource {
        private List<PersonDto> persons;
        private List<MedicalRecordDto> medicalrecords;
        // private List<AddressDto> addresses;
        // private Map<String,String> allergies;
        // private List<Medication> medications;
         private List<FireStationDto> firestations;

        public JsonSource() {
        }

        public List<PersonDto> getPersons() {
                return persons;
        }

        public void setPersons(List<PersonDto> persons) {
                this.persons = persons;
        }


        public List<MedicalRecordDto> getMedicalrecords() {
                return medicalrecords;
        }

        public void setMedicalrecords(List<MedicalRecordDto> medicalrecords) {
                this.medicalrecords = medicalrecords;
        }

        public List<FireStationDto> getFirestations() {
                return firestations;
        }

        public void setFirestations(List<FireStationDto> firestations) {
                this.firestations = firestations;
        }
}
