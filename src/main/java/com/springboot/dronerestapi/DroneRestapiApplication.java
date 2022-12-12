package com.springboot.dronerestapi;


import com.springboot.dronerestapi.enums.State;
import com.springboot.dronerestapi.exception.DroneNotFoundException;
import com.springboot.dronerestapi.model.Drone;
import com.springboot.dronerestapi.model.Medication;
import com.springboot.dronerestapi.repository.DroneRepository;
import com.springboot.dronerestapi.repository.MedicationRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@SpringBootApplication
@EnableScheduling
@AllArgsConstructor
@Component
public class DroneRestapiApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(Drone.class);
	private final DroneRepository droneRepository;
	private final MedicationRepository medicationRepository;

	public static void main(String[] args) {SpringApplication.run(DroneRestapiApplication.class, args);}

	@Override
	public void run(String... args) throws Exception {
		if(!(droneRepository.findAll().size() > 0)) {
			String str = "6G/8HD/876R";
			byte[] strImage = str.getBytes();
			droneRepository.deleteAll();
			droneRepository.save(new Drone("21", "LightWeight", "9he", new BigDecimal(0.88), State.IDLE));
			droneRepository.save(new Drone("22", "heavyWeight", "u74", new BigDecimal(0.57), State.IDLE));
			droneRepository.findAll().forEach(drone -> {
				logger.info("{}", drone);

			Drone drone1=droneRepository.findById("21").orElseThrow(()-> new DroneNotFoundException("drone with id not found"));
			drone1.setState(State.LOADED);
			medicationRepository.save(new Medication("yb8BEYErTYrfd-_", 0.12, "VTG3SS3HHR-_", strImage, State.LOADING, drone ));
			medicationRepository.findAll().forEach(medication -> {
				logger.info("{}", medication);
				});
			});
		}

	}

}
