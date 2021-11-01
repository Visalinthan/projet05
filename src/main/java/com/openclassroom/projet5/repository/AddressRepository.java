package com.openclassroom.projet5.repository;

import com.openclassroom.projet5.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

    @Query("SELECT a FROM Address a WHERE a.address = :address")
    Optional<Address> findByAddress(@Param("address") String address);
}
