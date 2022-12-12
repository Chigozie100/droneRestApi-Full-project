package com.springboot.dronerestapi.dtos.responses;

import lombok.Data;

@Data
public class MedicationResponseDto {
    private Long id;
    private String name;
    private double weight;
    private String code;
    private String image;
}
