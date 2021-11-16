package com.openclassroom.projet5.repository;

import com.openclassroom.projet5.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {

    @Modifying
    @Query("DELETE Person p WHERE p.firstName = :firstName AND p.lastName = :lastName")
    void deleteByNames(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
