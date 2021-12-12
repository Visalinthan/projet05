package com.openclassroom.projet5.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AllergyTest {

    private Allergy allergy;

    @Test
    public void constructorAllergy(){
        allergy = new Allergy(1L,"pollen");
        assertThat(allergy.getId()).isEqualTo(1L);
        assertThat(allergy.getName()).isEqualTo("pollen");
    }

}