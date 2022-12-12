package com.springboot.dronerestapi.dtos.responses;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DroneBatteryResponse {
    private BigDecimal battery;
}
