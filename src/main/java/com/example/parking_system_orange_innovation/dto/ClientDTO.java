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

    private String name;

    private String userName;

    private String password;

    private String role;

    private String email;

    private String phone_number;

    private boolean IsVIP;

    private Instant registrationDate;

    private boolean IsActive;

}