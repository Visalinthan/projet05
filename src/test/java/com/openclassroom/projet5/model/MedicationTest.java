package com.openclassroom.projet5.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MedicationTest {

    private Medication medication;

    @Test
    public void constructorMedication(){
        medication = new Medication(1L,"doliprane","100mg");
        assertThat(medication.getId()).isEqualTo(1L);
        assertThat(medication.getName()).isEqualTo("doliprane");
        assertThat(medication.getDosage()).isEqualTo("100mg");
    }

}