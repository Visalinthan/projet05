package com.openclassroom.projet5.repository;

import com.openclassroom.projet5.model.Address;
import com.openclassroom.projet5.model.MedicalRecord;
import com.openclassroom.projet5.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord,Long> {
}
