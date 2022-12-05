package com.example.parking_system_orange_innovation.car;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Car {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    private String plateNumber;

    private String color;

    private Boolean isParked;

    private Instant registrationDate;

    private Boolean isActive;

    private Boolean isAssigned;

}
