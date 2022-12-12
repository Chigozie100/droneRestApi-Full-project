package com.springboot.dronerestapi.dtos.requests;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class MedicationRequestDto {
    @Pattern(regexp="^[a-zA-Z0-9_-]*$", message="allowed only letters, numbers, ‘-‘, ‘_’")
    private String name;
    private double weight;
    @Pattern(regexp="^[A-Z0-9_-]*$", message="allowed only Upper case, numbers, ‘-‘, ‘_’")
    private String code;
}
