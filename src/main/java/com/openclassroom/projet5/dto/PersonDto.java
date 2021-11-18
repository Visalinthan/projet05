package com.openclassroom.projet5.dto;

import com.openclassroom.projet5.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PersonDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate birthdate;
    private String address;
    private Integer zip;
    private String city;


}
