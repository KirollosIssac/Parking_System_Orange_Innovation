package com.example.parking_system_orange_innovation.parking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "parkings")
public class ParkingController {

    @Autowired
    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping(path = "/parkingsHistory")
    public List<Parking> getParkings() {
        return parkingService.getAllParkings();
    }

    @GetMapping(path = "/clientHistory/{clientID}")
    public Optional<Parking> getCLientParkings (@PathVariable("clientID") Long clientID) {
        return parkingService.clientHistory(clientID);
    }

    @GetMapping(path = "/carHistory/{carID}")
    public Optional<Parking> getCarParkings (@PathVariable("carID") Long carID) {
        return parkingService.carHistory(carID);
    }

    @PostMapping(path = "/addParking")
    public Parking addParking(@RequestBody Parking parking) throws VIPSlotException {
        parkingService.addParking(parking);
        return parking;
    }

    @DeleteMapping(path = "/deleteParkings")
    public void deleteParkings() {
        parkingService.deleteParkings();
    }
}
