package com.openclassroom.projet5.repository;

import com.openclassroom.projet5.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication,Long> {
}
