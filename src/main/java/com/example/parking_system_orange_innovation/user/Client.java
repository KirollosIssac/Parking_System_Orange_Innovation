package com.example.parking_system_orange_innovation.user;

import com.example.parking_system_orange_innovation.car.Car;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

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

    @OneToMany
    private List<Car> cars;

    private String name;

    private String userName;

    private String email;

    private String password;

    private String phone_number;

    private boolean IsVIP;

    private Instant registrationDate;

    private boolean IsActive;

}
