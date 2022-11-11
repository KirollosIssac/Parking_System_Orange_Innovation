package com.example.parking_system_orange_innovation.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/getCars")
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/getCars/{clientID}")
    public Optional<Car> getCars(@PathVariable("clientID") Long clientID) {
        return carService.getCars(clientID);
    }

    @PostMapping("/addCar")
    public Car addCar(@RequestBody Car car) throws CarAlreadyExistsException {
        carService.addCar(car);
        return car;
    }

    @PutMapping("/updateCar")
    public Optional<Car> updateCar(@RequestBody Car car) {
        carService.updateCar(car);
        return Optional.of(car);
    }

    @DeleteMapping("/deleteCar/{carID}")
    public void deleteCar(@PathVariable("carID") Long carID) {
        carService.deleteCar(carID);
    }
}
