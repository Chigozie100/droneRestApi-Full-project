package com.springboot.dronerestapi.repository;

import com.springboot.dronerestapi.enums.State;
import com.springboot.dronerestapi.model.Drone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DroneRepository extends JpaRepository<Drone, String> {
    Optional<Drone> findBySerialNumber(String serialNumber);
    Page<Drone> findDronesByState(State state, PageRequest page);

}
