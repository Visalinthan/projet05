package com.openclassroom.projet5.repository;

import com.openclassroom.projet5.dto.FireStationDto;
import com.openclassroom.projet5.model.FireStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FireStationRepository extends JpaRepository<FireStation,Long> {

    List<FireStation> findByStation(int station);

}
