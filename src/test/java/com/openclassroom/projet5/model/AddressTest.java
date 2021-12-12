package com.openclassroom.projet5.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    private Address address;

    private static Address getAddress(){
        Address address = new Address();
        address.setId(1L);
        address.setAddress("10 rue jo");
        address.setZip(93330);
        address.setCity("lyon");

        return address;
    }

    @Test
    public void constructorAddress(){
        address = new Address(getAddress().getId(),getAddress().getAddress(),getAddress().getCity(),getAddress().getZip());
        assertThat(address.getId()).isEqualTo(getAddress().getId());
        assertThat(address.getAddress()).isEqualTo(getAddress().getAddress());
        assertThat(address.getZip()).isEqualTo(getAddress().getZip());
        assertThat(address.getCity()).isEqualTo(getAddress().getCity());
    }

}