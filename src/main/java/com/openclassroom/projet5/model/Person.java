package com.openclassroom.projet5.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Person {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;
    @Column(name="firstName")
    private String firstName;
    @Column(name="lastName")
    private String lastName;
    private String email;
    private String phone;

    private LocalDate birthdate;

    @JoinColumn(name = "address_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Address address;

    @OneToOne(mappedBy = "person")
    private MedicalRecord medicalRecord;

    public Long CalculAge(LocalDate birthdate) {
        return LocalDate.from(birthdate).until(LocalDate.now(), ChronoUnit.YEARS);
    }

}
