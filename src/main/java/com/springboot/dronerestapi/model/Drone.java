package com.springboot.dronerestapi.model;

import com.springboot.dronerestapi.enums.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "drone")
public class Drone {
    @Id
    @Column(name = "serial_no")
    private String serialNumber;

    @Column(name = "model") // lightWeight, middleWeight, cruiserWeight,
    private String model;

    @Column(name = "weight_limit")
    private String weightLimit;

    @Column(name = "battery")
    private BigDecimal battery;

    @Column(name = "drone_state") // IDLE, LOADING, LOADED, DELIVERING,
    @Enumerated(EnumType.STRING)
    private State state;

}





