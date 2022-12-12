package com.springboot.dronerestapi.service.impl;

import com.springboot.dronerestapi.dtos.requests.DroneRequestDto;
import com.springboot.dronerestapi.dtos.requests.MedicationRequestDto;
import com.springboot.dronerestapi.dtos.responses.DroneBatteryResponse;
import com.springboot.dronerestapi.dtos.responses.DroneResponseDto;
import com.springboot.dronerestapi.dtos.responses.MedicationResponseDto;
import com.springboot.dronerestapi.enums.State;
import com.springboot.dronerestapi.exception.DroneNotFoundException;
import com.springboot.dronerestapi.exception.DroneOverLoadedException;
import com.springboot.dronerestapi.model.Drone;
import com.springboot.dronerestapi.model.Medication;
import com.springboot.dronerestapi.repository.DroneRepository;
import com.springboot.dronerestapi.repository.MedicationRepository;
import com.springboot.dronerestapi.service.DroneService;
import com.springboot.dronerestapi.utils.AppConstants;
import com.springboot.dronerestapi.utils.Mapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Component
@Slf4j
public class DroneServiceImpl implements DroneService {
    private static final Logger logger = LoggerFactory.getLogger(Drone.class);
    private final DroneRepository droneRepository;

    private final MedicationRepository medicationRepository;

    @Override
    public DroneResponseDto createDrone(DroneRequestDto requestDto) {
        Drone drone = Mapper.mapToEntity(requestDto);
        drone.setState(State.IDLE);
        return Mapper.mapToDto(droneRepository.save(drone));
    }

    @Override
    public MedicationResponseDto loadDrone(MedicationRequestDto requestDto, String id) {
        Drone drone = droneRepository.findById(id).orElseThrow(()-> new DroneNotFoundException("Drone with id not found"));
        drone.setState(State.LOADED);
        Medication medication = Mapper.mapToEntity(requestDto);

        if (medication.getWeight() > AppConstants.DEFAULT_DRONE_SIZE) {
            throw new DroneOverLoadedException("The weight limit has exceeded");
        }

        if (drone.getBattery().compareTo(new BigDecimal(AppConstants.DEFAULT_BATTERY_SIZE)) < 0){
            throw new DroneOverLoadedException("Drone cannot load. battery is below 25%. Please recharge battery and load again");

        }
        medication.setState(State.LOADING);
        medication.setDrone(drone);

        return Mapper.mapToDto(medicationRepository.save(medication));
    }

    public String uploadMedicationImage(MultipartFile file, Long id) throws IOException {
        Medication medication =medicationRepository.findById(id).orElseThrow(()-> new DroneNotFoundException("Drone with id not found"));
        medication.setImage(Mapper.compressImage(file.getBytes()));
        medicationRepository.save(medication);

        if (medication != null){
            return "file uploaded successfully :" + file.getOriginalFilename();
        }
        return null;
    }

    @Override
    public MedicationResponseDto checkMedication(String id) {
        Drone drone=droneRepository.findBySerialNumber(id).orElseThrow(()-> new DroneNotFoundException("drone id not found"));
        Medication medication=medicationRepository.findMedicationByDrone(drone);
        if (medication == null){
            throw new DroneNotFoundException("No load Medication details for the specified drone");
        }

        MedicationResponseDto medicationResponseDto=Mapper.mapToDto(medication);
        return medicationResponseDto;
    }

    @Override
    public Page<Drone> checkAvailableDrones(int page, int size) {
        return droneRepository.findDronesByState(State.IDLE, PageRequest.of(page, size));
    }

    @Override
    public String checkDroneBatteryLevel(String id) {
        Drone drone=droneRepository.findById(id).orElseThrow(()-> new DroneNotFoundException("Drone with id not found"));
        String batteryLevel =String.valueOf(drone.getBattery());
        return batteryLevel;
    }

    @Scheduled(fixedRate = 5000)
    public void ScheduleTaskWithBatteryLevel(){
        logger.info("{}",droneRepository.findAll().stream().map(a->"Drone with id "+a.getSerialNumber()+" battery level is "+a.getBattery()).collect(Collectors.toList()));

    }



}
