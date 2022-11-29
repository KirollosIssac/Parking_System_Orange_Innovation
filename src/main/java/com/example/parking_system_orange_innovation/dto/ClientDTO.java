package com.example.parking_system_orange_innovation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClientDTO {

    private Long id;

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
