package com.example.parking_system_orange_innovation.slot;

import com.example.parking_system_orange_innovation.car.Car;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Slot {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @OneToOne
    private Car car;

    private Boolean isVIP;

    private Boolean isAvailable;

}
