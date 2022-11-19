package com.example.parking_system_orange_innovation.parking;

import com.example.parking_system_orange_innovation.car.Car;
import com.example.parking_system_orange_innovation.car.CarService;
import com.example.parking_system_orange_innovation.slot.*;
import com.example.parking_system_orange_innovation.user.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingService {

    @Autowired
    private final ParkingRepository parkingRepository;

    @Autowired
    private final SlotService slotService;

    @Autowired
    private final CarService carService;

    @Autowired
    private final ClientService clientService;

    public ParkingService(ParkingRepository parkingRepository, SlotService slotService, CarService carService, ClientService clientService) {
        this.parkingRepository = parkingRepository;
        this.slotService = slotService;
        this.carService = carService;
        this.clientService = clientService;
    }

    public List<Parking> getAllParkings() {
        return parkingRepository.findAll();
    }

    public List<Parking> carHistory(Long carID) {
        return parkingRepository.findParkingsBySlot_Car_Id(carID);
    }

    @Transactional
    public Parking addParking(Long slotId, Long carId) throws VIPSlotException, CarNotFoundException {
        Optional<Slot> slot = slotService.getSlot(slotId);
        Optional<Car> car = carService.getCar(carId);
        Parking parking = Parking.builder().slot(slot.get()).carId(car.get().getId())
                .carPlateNumber(car.get().getPlateNumber()).carColor(car.get().getColor())
                .startParking(Instant.now()).build();
        slotService.assignSlot(slot.get().getId(), car.get().getId());
        parkingRepository.save(parking);
        return parking;
    }

    @Transactional
    public void endParking(Long parkingId) {
        Optional<Parking> parking = parkingRepository.findById(parkingId);
        slotService.freeSlot(parking.get().getSlot().getId());
        parking.get().setEndParking(Instant.now());
    }

    public void deleteParkings() {
        List<Parking> parkings = parkingRepository.findAll();
        for (Parking parking : parkings) {
            Optional<Car> car = Optional.ofNullable(parking.getSlot().getCar());
            if (car.isPresent())
                slotService.freeSlot(parking.getSlot().getId());
        }
        parkingRepository.deleteAll();
    }

}
