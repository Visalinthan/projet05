package com.openclassroom.projet5.repository;

import com.openclassroom.projet5.model.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergyRepository extends JpaRepository<Allergy,Long> {
}
