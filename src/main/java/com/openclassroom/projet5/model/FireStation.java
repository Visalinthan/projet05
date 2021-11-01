package com.openclassroom.projet5.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FireStation {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;

    @JoinColumn(name = "address_id")
    @ManyToOne
    private Address address;

    private int station;


}
