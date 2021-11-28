package com.openclassroom.projet5.repository;

import com.openclassroom.projet5.model.Address;
import com.openclassroom.projet5.model.Allergy;
import com.openclassroom.projet5.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AllergyRepository extends JpaRepository<Allergy,Long> {

    @Query("SELECT a FROM Allergy a WHERE a.name = :allergy")
    Optional<Allergy> findByAllergy(@Param("allergy") String allergy);

}
