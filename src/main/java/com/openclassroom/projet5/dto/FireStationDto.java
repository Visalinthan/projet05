package com.openclassroom.projet5.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireStationDto {

    private Long id;
    private String address;
    private Integer zip;
    private String city;
    private int station;
}
