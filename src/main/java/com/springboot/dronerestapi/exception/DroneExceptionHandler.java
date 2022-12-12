package com.springboot.dronerestapi.exception;

import com.springboot.dronerestapi.dtos.responses.APIResponse;
import com.springboot.dronerestapi.utils.Responder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class DroneExceptionHandler{

    @ExceptionHandler(DroneNotFoundException.class)
    public ResponseEntity<APIResponse> droneNotFound(DroneNotFoundException ex){
        return Responder.notFound(ex.getMessage());
    }

    @ExceptionHandler(DroneOverLoadedException.class)
    public ResponseEntity<APIResponse> droneOverloaded(DroneOverLoadedException ex){
        return Responder.notFound(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }
}
