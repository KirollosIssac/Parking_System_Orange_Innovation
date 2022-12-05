package com.example.parking_system_orange_innovation.dto;
import com.example.parking_system_orange_innovation.car.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CurrentClientDTO {

    private Long userId;
    private String userName;
    private Boolean isVIP;
    private Boolean isActive;
    private Long carId;
    private Boolean isParked;

}
