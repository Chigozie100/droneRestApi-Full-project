package com.springboot.dronerestapi.repository;

import com.springboot.dronerestapi.model.Drone;
import com.springboot.dronerestapi.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
    Medication findMedicationByDrone(Drone drone);
}
