package com.example.parking_system_orange_innovation.user;

import com.example.parking_system_orange_innovation.car.Car;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Client {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @OneToOne
    private Car car;

    private String name;

    private String userName;

    private String password;

    private String role;

    private String email;

    private String phoneNumber;

    private Boolean isVIP;

    private Instant registrationDate;

    private Boolean isActive;

}
