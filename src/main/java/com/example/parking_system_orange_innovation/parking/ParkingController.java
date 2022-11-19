package com.example.parking_system_orange_innovation.parking;

import com.example.parking_system_orange_innovation.dto.SlotCarDTO;
import com.example.parking_system_orange_innovation.slot.CarNotFoundException;
import com.example.parking_system_orange_innovation.slot.VIPSlotException;
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

    @GetMapping(path = "/carHistory/{carID}")
    public List<Parking> getCarParkings (@PathVariable("carID") Long carID) {
        return parkingService.carHistory(carID);
    }

    @PostMapping(path = "/addParking")
    public Parking addParking(@RequestBody SlotCarDTO slotCarDTO) throws VIPSlotException, CarNotFoundException {
        return parkingService.addParking(slotCarDTO.getSlotId(), slotCarDTO.getCarId());
    }

    @PutMapping(path = "/endParking/{parkingId}")
    public void endParking(@PathVariable("parkingId") Long parkingId) {
        parkingService.endParking(parkingId);
    }

    @DeleteMapping(path = "/deleteParkings")
    public void deleteParkings() {
        parkingService.deleteParkings();
    }
}
