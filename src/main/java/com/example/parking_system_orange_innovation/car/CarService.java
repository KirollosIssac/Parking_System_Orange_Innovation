package com.example.parking_system_orange_innovation.car;

import com.example.parking_system_orange_innovation.user.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    @Autowired
    private final CarRepository carRepository;

    @Autowired
    private final ClientService clientService;

    public CarService(CarRepository carRepository, ClientService clientService) {
        this.carRepository = carRepository;
        this.clientService = clientService;
    }

    public List<Car> getAllCars() {
        //System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0]);
        return carRepository.findAll();
    }

    public Optional<Car> getCar(Long id) {
        return carRepository.findById(id);
    }

    @Transactional
    public Optional<Car> updateCar(Car car) {
        Optional<Car> existingCar = carRepository.findCarByPlateNumber(car.getPlateNumber());
        existingCar.get().setIsParked(car.getIsParked());
        existingCar.get().setIsActive(car.getIsActive());
        return existingCar;
    }

    public Car addCar(Car car) throws CarAlreadyExistsException {
        Optional<Car> optionalCar = carRepository.findCarByPlateNumber(car.getPlateNumber());
        if (optionalCar.isPresent())
            throw new CarAlreadyExistsException();
        car.setIsParked(false);
        car.setRegistrationDate(Instant.now());
        car.setIsActive(true);
        carRepository.save(car);
        return car;
    }

    @Transactional
    public void deleteCar(Long id) {
        clientService.deleteClientCar(id);
        carRepository.deleteCarById(id);
    }

}
