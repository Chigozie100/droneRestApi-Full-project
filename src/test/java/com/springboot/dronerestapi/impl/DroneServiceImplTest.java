package com.springboot.dronerestapi.impl;

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
import com.springboot.dronerestapi.service.impl.DroneServiceImpl;
import com.springboot.dronerestapi.utils.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class DroneServiceImplTest {
    @Mock
    DroneRepository droneRepository;

    @InjectMocks
    DroneServiceImpl droneService;
    @Mock
    MedicationRepository medicationRepository;


    Drone drone;

    DroneRequestDto droneRequestDto;
    MedicationRequestDto medicationRequestDto;

    Medication medication;
    String DUMMY_ID = "1";
    int DRONE_PAGE = 1;
    int DRONE_SIZE = 5;
    List<Drone> droneList = new ArrayList<>();

    @BeforeEach
    void setUp(){

        MockitoAnnotations.initMocks(this);

        drone = new Drone();

        drone.setSerialNumber("tr1234");
        drone.setModel("goit");
        drone.setWeightLimit("7r5");
        drone.setBattery(new BigDecimal(0.55));

        droneRequestDto = new DroneRequestDto();
        droneRequestDto.setSerialNumber("8uy");
        droneRequestDto.setModel("uyt");
        droneRequestDto.setWeightLimit("y7f5");
        droneRequestDto.setBattery(new BigDecimal(0.76));

         medicationRequestDto = new MedicationRequestDto();
         medicationRequestDto.setName("Paracetamol");
         medicationRequestDto.setWeight(0.19);
         medicationRequestDto.setCode("DGY767");

         medication = new Medication();
         medication.setDrone(drone);
         medication.setState(State.LOADED);
         medication.setName("Paracetamol");
         droneList.add(drone);
    }

    @Test
    @DisplayName("create a drone")
    void createDrone() {

        when(droneRepository.findBySerialNumber("tt12")).thenReturn(null);
        when(droneRepository.save(any(Drone.class))).thenReturn(drone);
        DroneResponseDto droneResponseDto=droneService.createDrone(droneRequestDto);

        assertNotNull(droneResponseDto);
        assertEquals(drone.getSerialNumber(), droneResponseDto.getSerialNumber());

    }

    @Test
    void loadDrone() {
        Mockito.when(droneRepository.findById(DUMMY_ID)).thenReturn(Optional.ofNullable(drone));
        Mockito.when(medicationRepository.save(any(Medication.class))).thenReturn(medication);

        MedicationResponseDto medicationResponseDto = droneService.loadDrone(medicationRequestDto,DUMMY_ID);

        assertEquals(medication.getName(),medicationResponseDto.getName());
        assertEquals(medication.getWeight(),medicationResponseDto.getWeight());
    }
    @Test
    void should_fail_when_medication_weight_is_greater_than_default_drone_size(){
        medicationRequestDto.setWeight(3.6);
        Mockito.when(droneRepository.findById(DUMMY_ID)).thenReturn(Optional.ofNullable(drone));
       Exception e = assertThrows(DroneOverLoadedException.class,(()->droneService.loadDrone(medicationRequestDto,DUMMY_ID)));
       assertEquals("The weight limit has exceeded",e.getMessage());
    }
    @Test
    void should_fail_when_drone_weight_is_less_than_default_battery_size(){
        drone.setBattery(BigDecimal.valueOf(0.1));
        Mockito.when(droneRepository.findById(DUMMY_ID)).thenReturn(Optional.ofNullable(drone));
        Exception e = assertThrows(DroneOverLoadedException.class,(()->droneService.loadDrone(medicationRequestDto,DUMMY_ID)));
        assertEquals("Drone cannot load. battery is below 25%. Please recharge battery and load again",e.getMessage());
    }


    @Test
    void checkMedication() {
        Mockito.when(droneRepository.findBySerialNumber(DUMMY_ID)).thenReturn(Optional.ofNullable(drone));
        Mockito.when(medicationRepository.findMedicationByDrone(drone)).thenReturn(medication);

       MedicationResponseDto medicationResponseDto= droneService.checkMedication(DUMMY_ID);

        assertEquals(medication.getName(),medicationResponseDto.getName());
        assertEquals(medication.getWeight(),medicationResponseDto.getWeight());
    }

    @Test
    void should_fail_when_medication_is_null(){
        Mockito.when(droneRepository.findBySerialNumber(DUMMY_ID)).thenReturn(Optional.ofNullable(drone));
        Exception e=assertThrows(DroneNotFoundException.class, (()-> droneService.checkMedication(DUMMY_ID)));
        assertEquals("No load Medication details for the specified drone", e.getMessage());
    }

    @Test
    void checkAvailableDrones() {
        Page<Drone> pages = new PageImpl<>(droneList);
        Mockito.when(droneRepository.findDronesByState(State.IDLE, PageRequest.of(DRONE_PAGE, DRONE_SIZE))).thenReturn(pages);
        Page<Drone> dronePage=droneService.checkAvailableDrones(DRONE_PAGE, DRONE_SIZE);
        assertEquals(drone.getWeightLimit(), dronePage.getContent().get(0).getWeightLimit());
    }


    @Test
    void checkDroneBatteryLevel() {
        Mockito.when(droneRepository.findById(DUMMY_ID)).thenReturn(Optional.ofNullable(drone));
        String droneBatteryResponse =droneService.checkDroneBatteryLevel(DUMMY_ID);
        assertEquals(String.valueOf(drone.getBattery()), droneBatteryResponse);
    }
}