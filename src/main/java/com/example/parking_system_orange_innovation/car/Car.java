package com.example.parking_system_orange_innovation.car;

import com.example.parking_system_orange_innovation.user.Client;
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

    @OneToOne
    private Client client;

    private String plateNumber;

    private String color;

    private boolean IsParked;

    private Instant registrationDate;

    private boolean IsActive;

}
