package com.example.parking_system_orange_innovation.car_test;

import com.example.parking_system_orange_innovation.car.*;
import com.example.parking_system_orange_innovation.user.CarIsNotAssignedToClient;
import com.example.parking_system_orange_innovation.user.Client;
import com.example.parking_system_orange_innovation.user.ClientRepository;
import com.example.parking_system_orange_innovation.user.ClientService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    CarRepository carRepository;

    @InjectMocks
    CarService carService;


    @Test
    public void updateCar() throws CarIsCurrentlyParkedException, CarNotFoundException {
            Car car = Car.builder()
                    .plateNumber("111")
                    .color("Black")
                    .isAssigned(true)
                    .isActive(false)
                    .isParked(true)
                    .build();
        boolean thrown = false;
        Mockito.when(carRepository.findCarByPlateNumber("111")).thenReturn(Optional.of(car));
        try {
            carService.updateCar(car);
        }
        catch (CarIsCurrentlyParkedException carIsCurrentlyParkedException) {
            thrown = true;
        }
        assertThat(thrown).isTrue();
        car.setIsActive(true);
        Optional<Car> updatedCar = carService.updateCar(car);
        assertThat(updatedCar.get().getIsActive()).isTrue();
    }

    @Test
    public void addCar() throws CarAlreadyExistsException {
        Car car = Car.builder()
                .plateNumber("111")
                .color("Black")
                .build();
        Mockito.when(carRepository.findCarByPlateNumber("111")).thenReturn(Optional.of(car));
        boolean thrown = false;
        try {
            carService.addCar(car);
        } catch (CarAlreadyExistsException carAlreadyExistsException) {
            thrown = true;
        }
        assertThat(thrown).isTrue();
        Car addedCar = Car.builder()
                .plateNumber("222")
                .color("Black")
                .build();
        Car returnedCar = carService.addCar(addedCar);
        assertThat(returnedCar.getIsActive()).isFalse();
        assertThat(returnedCar.getIsParked()).isFalse();
        assertThat(returnedCar.getIsAssigned()).isFalse();
        assertThat(returnedCar.getRegistrationDate()).isBefore(Instant.now());
    }

    @Test
    public void deleteCar() throws CarIsNotAssignedToClient {
        Car car = Car.builder()
                .plateNumber("123")
                .color("Black")
                .isActive(true)
                .isParked(true)
                .isAssigned(true)
                .build();
        Mockito.when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        boolean thrown = false;
        try {
            carService.deleteCar(1L);
        } catch (CarIsCurrentlyParkedException carIsCurrentlyParkedException) {
            thrown = true;
        }
        assertThat(thrown).isTrue();
    }

}
