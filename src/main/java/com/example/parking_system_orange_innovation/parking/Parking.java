package com.example.parking_system_orange_innovation.parking;

import com.example.parking_system_orange_innovation.car.Car;
import com.example.parking_system_orange_innovation.slot.Slot;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Parking {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @OneToOne
    private Slot slot;

    @OneToOne
    private Car car;

    private Instant startParking;

    private Instant endParking;

}
