package com.springboot.dronerestapi.controller;

import com.springboot.dronerestapi.dtos.requests.DroneRequestDto;
import com.springboot.dronerestapi.dtos.requests.MedicationRequestDto;
import com.springboot.dronerestapi.dtos.responses.APIResponse;
import com.springboot.dronerestapi.dtos.responses.DroneBatteryResponse;
import com.springboot.dronerestapi.dtos.responses.DroneResponseDto;
import com.springboot.dronerestapi.dtos.responses.MedicationResponseDto;
import com.springboot.dronerestapi.model.Drone;
import com.springboot.dronerestapi.service.DroneService;
import com.springboot.dronerestapi.utils.Mapper;
import com.springboot.dronerestapi.utils.Responder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@AutoConfigureMockMvc
class DroneControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    DroneService droneService;

    @MockBean
    Responder responder;

    @InjectMocks
    DroneController droneController;

    Drone drone;
    DroneRequestDto droneRequestDto;
    ResponseEntity<APIResponse> responseResponseEntity;
    DroneResponseDto droneResponseDto;

    MedicationRequestDto medicationRequestDto;

    MedicationResponseDto medicationResponseDto;

    String DUMMY_ID = "tr1234";

    int PAGE_NO = 0;
    int SIZE_NO = 7;

    List<Drone> droneList;

    List<DroneResponseDto> droneResponseDtoList;

    DroneBatteryResponse droneBatteryResponse;

    @BeforeEach
    void setUp() {
        drone = new Drone();
        drone.setSerialNumber("tr1234");
        drone.setModel("goit");
        drone.setWeightLimit("y7f");
        drone.setBattery(new BigDecimal(0.55));

        droneList = new ArrayList<>();
        droneList.add(drone);

        droneRequestDto = new DroneRequestDto();
        droneRequestDto.setSerialNumber("8uy");
        droneRequestDto.setModel("uyt");
        droneRequestDto.setWeightLimit("y7f");
        droneRequestDto.setBattery(new BigDecimal(0.76));

       droneResponseDto = new DroneResponseDto();
        droneResponseDto.setSerialNumber("90i");
        droneResponseDto.setModel("falcon9");
        droneResponseDto.setWeightLimit("y7f");
        droneResponseDto.setBattery(BigDecimal.valueOf(0.96));
        droneResponseDtoList = new ArrayList<>();
        droneResponseDtoList.add(droneResponseDto);


        responseResponseEntity = new ResponseEntity<>(new APIResponse<>("successful", true, drone), HttpStatus.OK);

        medicationRequestDto = new MedicationRequestDto();
        medicationRequestDto.setName("Paracetamol");
        medicationRequestDto.setWeight(0.19);
        medicationRequestDto.setCode("DGY767");

        medicationResponseDto = new MedicationResponseDto();
        medicationResponseDto.setId(23L);
        medicationResponseDto.setName("Paracetamol");
        medicationResponseDto.setWeight(0.98);
        medicationResponseDto.setImage("MedicationRequestDto");
        medicationResponseDto.setCode("UI87");

        droneBatteryResponse = new DroneBatteryResponse();
        droneBatteryResponse.setBattery(new BigDecimal(0.89));
    }

    @Test
    void createDrone() throws Exception {
        String url="/api/v1/createDrone";

        Mockito.when(droneService.createDrone(droneRequestDto)).thenReturn(droneResponseDto);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(Mapper.mapToJson(droneRequestDto))).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    @Test
    void loadDrone() throws Exception {
        String url = "/api/v1/loadDrone/1";
        Mockito.when(droneService.loadDrone(medicationRequestDto, DUMMY_ID)).thenReturn(medicationResponseDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(Mapper.mapToJson(medicationRequestDto))).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    @Test
    void getMedication() throws Exception {
        String url = "/api/v1/getMedication/2";
        Mockito.when(droneService.checkMedication(DUMMY_ID)).thenReturn(medicationResponseDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(Mapper.mapToJson(medicationResponseDto))).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);

    }

    @Test
    void checkAvailableDrone() throws Exception {
        Page<Drone> pages = new PageImpl<>(droneList);

        String url = "/api/v1/checkAvailableDrone?dronePage=0&droneSize=7";
        Mockito.when(droneService.checkAvailableDrones(PAGE_NO, SIZE_NO)).thenReturn(pages);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(Mapper.mapToJson(pages))).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);

    }

    @Test
    void checkDroneBatteryLevel() throws Exception {
        String url = "/api/v1/checkDroneBatteryLevel/1";
        Mockito.when(droneService.checkDroneBatteryLevel(DUMMY_ID)).thenReturn(String.valueOf(BigDecimal.valueOf(0.89)));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(Mapper.mapToJson(droneBatteryResponse))).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);

    }
}