package com.openclassroom.projet5.repository;

import com.openclassroom.projet5.dto.FireStationDto;
import com.openclassroom.projet5.model.Address;
import com.openclassroom.projet5.model.FireStation;
import com.openclassroom.projet5.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FireStationRepository extends JpaRepository<FireStation,Long> {

    @Query("SELECT f.station FROM FireStation f WHERE f.address.address = :address")
    int findFireStationByAddress(@Param("address") String address);

    @Query("SELECT f.address FROM FireStation f WHERE f.station = :station")
    List<Address> findAddressByStation(@Param("station") int station);

}
