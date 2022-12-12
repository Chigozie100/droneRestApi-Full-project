package com.springboot.dronerestapi.controller;

import com.springboot.dronerestapi.dtos.requests.DroneRequestDto;
import com.springboot.dronerestapi.dtos.requests.MedicationRequestDto;
import com.springboot.dronerestapi.dtos.responses.APIResponse;
import com.springboot.dronerestapi.service.DroneService;
import com.springboot.dronerestapi.utils.Responder;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class DroneController {
    private final DroneService service;

    private final Responder responder;

    @PostMapping("/createDrone")
    public ResponseEntity<APIResponse> createDrone(@Valid @RequestBody DroneRequestDto dto){
        return responder.okay(service.createDrone(dto));
    }

    @PutMapping("/loadDrone/{id}")
    public ResponseEntity<APIResponse> loadDrone(@RequestBody @Valid MedicationRequestDto requestDto,
                                                   @PathVariable String id){
        return responder.okay(service.loadDrone(requestDto,id));
    }

    @GetMapping("/getMedication/{id}")
    public ResponseEntity<APIResponse> getMedication(@PathVariable String id){
        return responder.okay(service.checkMedication(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<APIResponse> uploadMedicationImage(@RequestParam("image") MultipartFile file,
                                                   @PathVariable(name = "id") Long id) throws IOException {
        return responder.okay(service.uploadMedicationImage(file, id));
    }

    @GetMapping("/checkAvailableDrone")
    public ResponseEntity<APIResponse> checkAvailableDrone(@RequestParam("dronePage") int page,
                                                           @RequestParam("droneSize") int size){
        return responder.okay(service.checkAvailableDrones(page, size));
    }

    @GetMapping("/checkDroneBatteryLevel/{id}")
    public ResponseEntity<APIResponse> checkDroneBatteryLevel(@PathVariable(name = "id") String id){
        return responder.okay(service.checkDroneBatteryLevel(id));
    }
}

