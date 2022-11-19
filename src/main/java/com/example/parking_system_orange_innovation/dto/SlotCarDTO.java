package com.example.parking_system_orange_innovation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SlotCarDTO {

    private Long slotId;
    private Long carId;

}
