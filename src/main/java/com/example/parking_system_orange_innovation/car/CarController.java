package com.example.parking_system_orange_innovation.car;

import com.example.parking_system_orange_innovation.user.Client;
import com.example.parking_system_orange_innovation.user.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "cars")
public class CarController {

    @Autowired
    private final CarService carService;

    @Autowired
    private final ClientService clientService;

    public CarController(CarService carService, ClientService clientService) {
        this.carService = carService;
        this.clientService = clientService;
    }

    @GetMapping("/getCars")
    public ResponseEntity<List<Car>> getAllCars() {
        return new ResponseEntity<>(carService.getAllCars(), HttpStatus.OK);
    }

    @GetMapping("/getCarOwner/{carId}")
    public Optional<Client> getCarOwner(@PathVariable("carId") Long carId) {
        return clientService.getCarOwner(carId);
    }

    @PostMapping("/addCar")
    public ResponseEntity<Car> addCar(@RequestBody Car car) throws CarAlreadyExistsException {
        carService.addCar(car);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @PutMapping("/updateCar")
    public Optional<Car> updateCar(@RequestBody Car car) {
        carService.updateCar(car);
        return Optional.of(car);
    }

    @DeleteMapping("/deleteCar/{carId}")
    public void deleteCar(@PathVariable("carId") Long carId) {
        carService.deleteCar(carId);
    }

}
