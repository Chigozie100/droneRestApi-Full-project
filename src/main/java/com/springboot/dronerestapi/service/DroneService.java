package com.springboot.dronerestapi.service;

import com.springboot.dronerestapi.dtos.requests.DroneRequestDto;
import com.springboot.dronerestapi.dtos.requests.MedicationRequestDto;
import com.springboot.dronerestapi.dtos.responses.DroneBatteryResponse;
import com.springboot.dronerestapi.dtos.responses.DroneResponseDto;
import com.springboot.dronerestapi.dtos.responses.MedicationResponseDto;
import com.springboot.dronerestapi.model.Drone;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DroneService {
    DroneResponseDto createDrone(DroneRequestDto requestDto);

    MedicationResponseDto loadDrone(MedicationRequestDto requestDto, String id);
    MedicationResponseDto checkMedication(String id);
    String uploadMedicationImage(MultipartFile file, Long id) throws IOException;

    Page<Drone> checkAvailableDrones(int page, int size);

    String checkDroneBatteryLevel(String id);

}
