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
    public ResponseEntity<Optional<Client>> getCarOwner(@PathVariable("carId") Long carId) {
        Optional<Client> optionalClient = clientService.getCarOwner(carId);
        if (optionalClient.isPresent())
            return new ResponseEntity<>(optionalClient, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/addCar")
    public ResponseEntity<String> addCar(@RequestBody Car car) throws CarAlreadyExistsException {
        try {
            carService.addCar(car);
        } catch (CarAlreadyExistsException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Car added successfully!", HttpStatus.OK);
    }

    @PutMapping("/updateCar")
    public ResponseEntity<String> updateCar(@RequestBody Car car) {
        carService.updateCar(car);
        return new ResponseEntity<>("Car updated successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/deleteCar/{carId}")
    public ResponseEntity<String> deleteCar(@PathVariable("carId") Long carId) {
        carService.deleteCar(carId);
        return new ResponseEntity("Car deleted successfully!", HttpStatus.OK);
    }

}
