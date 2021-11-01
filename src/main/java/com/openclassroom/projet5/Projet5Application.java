package com.openclassroom.projet5;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroom.projet5.dto.MedicalRecordDto;
import com.openclassroom.projet5.mapper.FireStationMapper;
import com.openclassroom.projet5.mapper.PersonMapper;
import com.openclassroom.projet5.model.FireStation;
import com.openclassroom.projet5.model.Person;
import com.openclassroom.projet5.service.FireStationService;
import com.openclassroom.projet5.utils.JsonSource;
import com.openclassroom.projet5.service.PersonService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Projet5Application {

	private final PersonMapper personMapper;
	private final FireStationMapper fireStationMapper;

	private final Resource jsonSource;

	public Projet5Application(
			PersonMapper personMapper,
			FireStationMapper fireStationMapper,
			@Value("classpath:json/data.json") Resource jsonSource) {
		this.personMapper = personMapper;
		this.fireStationMapper = fireStationMapper;
		this.jsonSource = jsonSource;
	}

	public static void main(String[] args) {
		SpringApplication.run(Projet5Application.class, args);
	}

	@Bean
	@Transactional
	CommandLineRunner runner(PersonService personService, FireStationService fireStationService){
		return args -> {
			ObjectMapper mapper = new ObjectMapper();

			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
			//mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
			//JSON file to Java object
			JsonSource obj = mapper.readValue(jsonSource.getFile(), JsonSource.class);

			final List<MedicalRecordDto> medicalrecords = obj.getMedicalrecords();
			final List<Person> persons = obj.getPersons().stream()
					.map(p -> personMapper.toEntity(p, medicalrecords))
					.collect(Collectors.toList());

			personService.save(persons);


			final List<FireStation> fireStations = obj.getFirestations().stream()
					.map(fireStationDto -> fireStationMapper.toEntity(fireStationDto))
					.collect(Collectors.toList());

			fireStationService.save(fireStations);

		};

	}
}
