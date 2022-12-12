package com.springboot.dronerestapi.utils;

import com.springboot.dronerestapi.dtos.responses.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class Responder<T>{
    public ResponseEntity<APIResponse> okay(Object response){
        return new ResponseEntity<>(new APIResponse<>("Request Successful", true, response), HttpStatus.OK);
    }

    public static ResponseEntity<APIResponse> notFound(String message){
        return  new ResponseEntity<>(new APIResponse(message,true, null), HttpStatus.NOT_FOUND);
    }
}

