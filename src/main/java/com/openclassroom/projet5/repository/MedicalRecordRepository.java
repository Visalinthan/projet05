package com.openclassroom.projet5.repository;

import com.openclassroom.projet5.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord,Long> {

    @Query("SELECT m FROM MedicalRecord m WHERE m.person.firstName = :firstName AND  m.person.lastName = :lastName ")
    Optional<MedicalRecord> findByPerson(@Param("firstName") String firstName,@Param("lastName") String lastName);
}
