package com.springboot.dronerestapi.model;

import com.springboot.dronerestapi.enums.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medication")
public class Medication {
    @Id
    @Column(name = "Medication_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "weight")
    private double weight;

    @Column(name = "code")
    private String code;

    @Column(name = "medication_image")
    @Lob
    private byte[] image;

    @Column(name = "dateCreated")
    @CreationTimestamp
    private LocalDateTime loadMedicationDate;

    @Column(name = "drone_state") // IDLE, LOADING, LOADED, DELIVERING,
    @Enumerated(EnumType.STRING)
    private State state;

    @OneToOne
    @JoinColumn(name = "drone_id", nullable = false)
    private Drone drone;

    public Medication(String name, double weight, String code, byte[] image, State state, Drone drone) {
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.image = image;
        this.state = state;
        this.drone = drone;
    }


}
