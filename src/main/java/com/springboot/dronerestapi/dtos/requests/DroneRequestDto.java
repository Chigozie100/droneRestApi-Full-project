package com.springboot.dronerestapi.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DroneRequestDto {
    @NotNull
    @Size(max=100, message="serialNumber should not be more than 100 characters")
    private String serialNumber;
    @NotNull
    private String model;
    @NotNull
    @Size(max = 500, message="weightLimit should not be more than 500gr")
    private String weightLimit;
    @NotNull
    private BigDecimal battery;

}
