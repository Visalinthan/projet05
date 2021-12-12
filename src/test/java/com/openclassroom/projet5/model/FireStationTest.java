package com.openclassroom.projet5.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FireStationTest {
    private FireStation fireStation;
    private Long id = 1L;
    private static Address getAddress(){
        Address address = new Address();
        address.setId(1L);
        address.setAddress("10 rue jo");
        address.setZip(93330);
        address.setCity("lyon");

        return address;
    }

    @Test
    public void constructorFireStation(){
        fireStation = new FireStation(id,getAddress(),2);
        assertThat(fireStation.getId()).isEqualTo(1L);
        assertThat(fireStation.getAddress()).isEqualTo(getAddress());
        assertThat(fireStation.getStation()).isEqualTo(2);
    }

}