package com.springboot.dronerestapi.exception;

public class DroneOverLoadedException extends RuntimeException{
    public DroneOverLoadedException(String message){
        super(message);
    }
}
