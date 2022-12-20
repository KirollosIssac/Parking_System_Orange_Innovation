package com.example.parking_system_orange_innovation.car_test;

import com.example.parking_system_orange_innovation.car.*;
import com.example.parking_system_orange_innovation.user.CarIsNotAssignedToClient;
import com.example.parking_system_orange_innovation.user.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CarServiceTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ClientService clientService;

    private CarService carService;

    @BeforeEach
    void setUp() {
        this.carService = new CarService(this.carRepository, this.clientService);
    }

    @Test
    public void getAllCars() {
        Car RECORD_1 = Car.builder()
                .plateNumber("111")
                .color("Black")
                .build();
        Car RECORD_2 = Car.builder()
                .plateNumber("222")
                .color("White")
                .build();
        carRepository.saveAll(List.of(RECORD_1, RECORD_2));
        System.out.print(carRepository.findAll());
        List<Car> allCars = carService.getAllCars();
        assertThat(allCars.size()).isEqualTo(2);
        carRepository.deleteAll();
    }

    @Test
    public void getCar() {
        Car RECORD_3 = Car.builder()
                .plateNumber("333")
                .color("YELLOW")
                .build();
        carRepository.save(RECORD_3);
        assertThat(carRepository.existsById(1L)).isTrue();
        Optional<Car> optionalCar = carService.getCar(1L);
        assertThat(optionalCar.isPresent()).isTrue();
        carRepository.deleteAll();
    }

    @Test
    public void updateCar() throws CarNotFoundException, CarIsCurrentlyParkedException{
        Car RECORD_4 = Car.builder()
                .plateNumber("444")
                .color("Black")
                .isAssigned(true)
                .isActive(false)
                .isParked(false)
                .build();
        Car RECORD_5 = Car.builder()
                .plateNumber("555")
                .color("White")
                .isAssigned(true)
                .isActive(false)
                .isParked(true)
                .build();
        boolean thrown = false;
        try {
            carService.updateCar(RECORD_4);
        } catch (CarNotFoundException carNotFoundException){
            thrown = true;
        }
        assertThat(thrown).isTrue();
        carRepository.save(RECORD_5);
        thrown = false;
        try {
            carService.updateCar(RECORD_5);
        } catch (CarIsCurrentlyParkedException carIsCurrentlyParkedException){
            thrown = true;
        }
        assertThat(thrown).isTrue();
        carRepository.save(RECORD_4);
        RECORD_4.setIsActive(true);
        RECORD_4.setIsParked(true);
        Optional<Car> optionalCar = carService.updateCar(RECORD_4);
        assertThat(optionalCar.get().getIsActive()).isTrue();
        assertThat(optionalCar.get().getIsParked()).isTrue();
        carRepository.deleteAll();
    }

    @Test
    public void addCar() throws CarAlreadyExistsException {
        Car RECORD_6 = Car.builder()
                .plateNumber("666")
                .color("Black")
                .isAssigned(false)
                .isActive(false)
                .isParked(false)
                .build();
        carRepository.save(RECORD_6);
        boolean thrown = false;
        try {
            carService.addCar(RECORD_6);
        } catch (CarAlreadyExistsException e) {
            thrown = true;
        }
        assertThat(thrown).isTrue();
        Car RECORD_7 = Car.builder()
                .plateNumber("777")
                .color("Black")
                .build();
        Car car = carService.addCar(RECORD_7);
        assertThat(car.getIsParked()).isFalse();
        assertThat(car.getIsActive()).isFalse();
        assertThat(car.getIsAssigned()).isFalse();
        assertThat(car.getRegistrationDate()).isBefore(Instant.now());
    }

    @Test
    public void deleteCar() throws CarIsNotAssignedToClient {
        Car RECORD_7 = Car.builder()
                .plateNumber("777")
                .color("Black")
                .isAssigned(true)
                .isActive(true)
                .isParked(true)
                .build();
        carRepository.save(RECORD_7);
        boolean thrown = false;
        try {
            carService.deleteCar(1L);
        } catch (CarIsCurrentlyParkedException carIsCurrentlyParkedException) {
            thrown = true;
        }
        assertThat(thrown).isTrue();
    }

}
