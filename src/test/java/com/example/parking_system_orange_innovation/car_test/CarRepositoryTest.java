package com.example.parking_system_orange_innovation.car_test;

import com.example.parking_system_orange_innovation.car.Car;
import com.example.parking_system_orange_innovation.car.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Test
    public void findCarByPlateNumber() {
        Car car = Car.builder().plateNumber("123").color("Black").build();
        carRepository.save(car);
        Optional<Car> result = carRepository.findCarByPlateNumber("123");
        assertThat(result).isEqualTo(Optional.of(car));
    }

    @Test
    public void deleteCarById() {
        Car car = Car.builder().plateNumber("124").color("Black").build();
        carRepository.save(car);
        assertThat(carRepository.existsById(2L)).isTrue();
        carRepository.deleteCarById(2L);
        assertThat(carRepository.existsById(2L)).isFalse();
    }
}
