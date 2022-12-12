package com.springboot.dronerestapi.dtos.responses;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class DroneResponseDto {
    private String serialNumber;
    private String model;
    private String weightLimit;
    private BigDecimal battery;
}
