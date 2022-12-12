package com.springboot.dronerestapi.exception;

public class DroneNotFoundException extends RuntimeException{
    public DroneNotFoundException(String message){
        super(message);
    }
}
