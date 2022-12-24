package com.example.parking_system_orange_innovation.parking_test;

import com.example.parking_system_orange_innovation.car.Car;
import com.example.parking_system_orange_innovation.parking.Parking;
import com.example.parking_system_orange_innovation.parking.ParkingRepository;
import com.example.parking_system_orange_innovation.parking.ParkingService;
import com.example.parking_system_orange_innovation.slot.Slot;
import com.example.parking_system_orange_innovation.slot.SlotIsNotActiveException;
import com.example.parking_system_orange_innovation.slot.SlotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {

    @Mock
    ParkingRepository parkingRepository;

    @Mock
    SlotService slotService;

    @InjectMocks
    ParkingService parkingService;

    @Test
    public void endParking() throws SlotIsNotActiveException {
        Car car = Car.builder()
                .plateNumber("123")
                .color("Black")
                .isActive(true)
                .isParked(true)
                .isAssigned(true)
                .build();
        Slot slot = Slot.builder()
                .isVIP(false)
                .isActive(true)
                .car(car)
                .isAvailable(false)
                .build();
        Parking parking = Parking.builder()
                .slot(slot)
                .startParking(Instant.now())
                .isFinished(false)
                .carId(car.getId())
                .carPlateNumber(car.getPlateNumber())
                .carColor(car.getColor())
                .build();
        Mockito.when(parkingRepository.findParkingByCarIdAndIsFinishedIsFalse(car.getId())).thenReturn(Optional.of(parking));
        Optional<Parking> returnedParking = parkingService.endParking(car.getId());
        assertThat(returnedParking.get().getIsFinished()).isTrue();
        assertThat(returnedParking.get().getEndParking()).isBeforeOrEqualTo(Instant.now());
    }
}
