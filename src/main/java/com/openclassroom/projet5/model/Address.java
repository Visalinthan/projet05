package com.openclassroom.projet5.model;

import  lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@ToString
public class Address {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    private String address;
    private String city;
    private Integer zip;


}
