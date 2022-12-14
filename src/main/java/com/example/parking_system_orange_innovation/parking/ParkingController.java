package com.example.parking_system_orange_innovation.parking;

import com.example.parking_system_orange_innovation.dto.SlotCarDTO;
import com.example.parking_system_orange_innovation.slot.CarNotFoundException;
import com.example.parking_system_orange_innovation.slot.SlotIsNotActiveException;
import com.example.parking_system_orange_innovation.slot.VIPSlotException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Parking>> getParkings() {
        return new ResponseEntity<>(parkingService.getAllParkings(), HttpStatus.OK);
    }

    @GetMapping(path = "/carHistory/{carID}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public ResponseEntity<List<Parking>> getCarParkings (@PathVariable("carID") Long carID) {
        return new ResponseEntity<>(parkingService.carHistory(carID), HttpStatus.OK);
    }

    @PostMapping(path = "/startParking")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public ResponseEntity<String> startParking(@RequestBody SlotCarDTO slotCarDTO) throws VIPSlotException, CarNotFoundException {
        try {
            parkingService.startParking(slotCarDTO.getSlotId(), slotCarDTO.getCarId());
        } catch(Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Parked successfully!", HttpStatus.OK);
    }

    @PutMapping(path = "/endParking/{carId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public ResponseEntity<String> endParking(@PathVariable("carId") Long carId) throws SlotIsNotActiveException {
        parkingService.endParking(carId);
        return new ResponseEntity<>("Parking ended successfully!", HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteParkings")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteParkings() throws SlotIsNotActiveException {
        parkingService.deleteParkings();
        return new ResponseEntity<>("Parking history deleted successfully!", HttpStatus.OK);
    }
}
