package com.openclassroom.projet5.controller;

import com.openclassroom.projet5.dto.FireStationDto;
import com.openclassroom.projet5.dto.PersonDto;
import com.openclassroom.projet5.model.Address;
import com.openclassroom.projet5.model.FireStation;
import com.openclassroom.projet5.model.Person;
import com.openclassroom.projet5.repository.FireStationRepository;
import com.openclassroom.projet5.service.FireStationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class FireStationController {

    private final FireStationService fireStationService;
    private FireStationRepository fireStationRepository;

    public FireStationController(FireStationService fireStationService, FireStationRepository fireStationRepository) {
        this.fireStationService = fireStationService;
        this.fireStationRepository = fireStationRepository;
    }

    @GetMapping("/firestations")
    public ResponseEntity<List<FireStationDto>> getFireStations(){
        List<FireStationDto> fireStations = fireStationService.list();
        return ResponseEntity.ok().body(fireStations);
    }

    @PostMapping("/firestation")
    public ResponseEntity<FireStationDto> createFireStation(@RequestBody FireStationDto fireStationDto) throws  Exception{
        if(fireStationDto.getId() != null){
            throw new Exception("err");
        }

        FireStationDto result = fireStationService.save(fireStationDto);

        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/firestation/{id}")
    public ResponseEntity<FireStationDto> updateFireStation(@PathVariable("id") long id,@RequestBody FireStationDto fireStationDto) throws  Exception{

        FireStationDto result = fireStationService.update(id,fireStationDto);

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("firestation/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        fireStationService.delete(id);
        return ResponseEntity.ok().build();
    }
/*
    @RequestMapping(value="firestations", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<Person>> getPersonsByNumberStation(@RequestParam("stationNumber") int StationNumber){
        List<Person>  personDto =  fireStationService.listPersonByStationNumber(StationNumber);
        return ResponseEntity.ok().body(personDto);
    }

 */

  /*  @GetMapping("/firestations")
    public ResponseEntity<List<FireStationDto>> getPersonsByNumberStation(@RequestParam int stationNumber) {
            List<FireStationDto> fireStationDto =  fireStationRepository.findByStation(stationNumber).stream().collect(Collectors.toList());
            return ResponseEntity.ok().body(fireStationDto);
    }*/
}
