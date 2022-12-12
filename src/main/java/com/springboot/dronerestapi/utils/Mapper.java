package com.springboot.dronerestapi.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.dronerestapi.dtos.requests.DroneRequestDto;
import com.springboot.dronerestapi.dtos.requests.MedicationRequestDto;
import com.springboot.dronerestapi.dtos.responses.DroneResponseDto;
import com.springboot.dronerestapi.dtos.responses.MedicationResponseDto;
import com.springboot.dronerestapi.model.Drone;
import com.springboot.dronerestapi.model.Medication;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;

public class Mapper {
    public static Drone mapToEntity(DroneRequestDto requestDto){
        Drone drone = new Drone();
        drone.setSerialNumber(requestDto.getSerialNumber());
        drone.setModel(requestDto.getModel());
        drone.setWeightLimit(requestDto.getWeightLimit());
        drone.setBattery(requestDto.getBattery());
        return  drone;
    }

    public static DroneResponseDto mapToDto(Drone drone){
        DroneResponseDto responseDto = new DroneResponseDto();
        responseDto.setSerialNumber(drone.getSerialNumber());
        responseDto.setModel(drone.getModel());
        responseDto.setWeightLimit(drone.getWeightLimit());
        responseDto.setBattery(drone.getBattery());
        return responseDto;
    }

    public static Medication mapToEntity(MedicationRequestDto requestDto){
        Medication medication = new Medication();
        medication.setName(requestDto.getName());
        medication.setWeight(requestDto.getWeight());
        medication.setCode(requestDto.getCode());
        return medication;
    }

    public static MedicationResponseDto mapToDto(Medication medication){
        MedicationResponseDto medicationResponseDto = new MedicationResponseDto();
        medicationResponseDto.setId(medication.getId());
        medicationResponseDto.setName(medication.getName());
        medicationResponseDto.setWeight(medication.getWeight());
        medicationResponseDto.setCode(medication.getCode());
        return medicationResponseDto;
    }

    public static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

    public static String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

}
