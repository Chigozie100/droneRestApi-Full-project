package com.springboot.dronerestapi.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse <T>{
    private String message;
    private  Boolean success;
    private T payload;
}
