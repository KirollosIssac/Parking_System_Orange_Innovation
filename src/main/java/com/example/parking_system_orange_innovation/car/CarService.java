package com.example.parking_system_orange_innovation.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    @Autowired
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Transactional
    public Optional<Car> updateCar(Car car) {
        Optional<Car> optionalCar = carRepository.findCarByPlateNumber(car.getPlateNumber());
        optionalCar.get().setIsActive(car.isIsActive());
        optionalCar.get().setIsParked(car.isIsParked());
        //optionalCar.get().setClient(car.getClient());
        return optionalCar;
    }

    public Optional<Car> getCars(Long clientID) {
        return carRepository.getCars(clientID);
    }

    @Transactional
    public Car addCar(Car car) throws CarAlreadyExistsException {
        Optional<Car> optionalCar = carRepository.findCarByPlateNumber(car.getPlateNumber());
        if (optionalCar.isPresent())
            throw new CarAlreadyExistsException();
        car.setRegistrationDate(Instant.now());
        car.setIsActive(true);
        carRepository.save(car);
        return car;
    }

    public void deleteCar(Long carID) {
        carRepository.deleteAllById(Collections.singleton(carID));
    }
}
