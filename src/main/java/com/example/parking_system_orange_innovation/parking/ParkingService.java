package com.example.parking_system_orange_innovation.parking;

import com.example.parking_system_orange_innovation.car.Car;
import com.example.parking_system_orange_innovation.car.CarRepository;
import com.example.parking_system_orange_innovation.slot.Slot;
import com.example.parking_system_orange_innovation.slot.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingService {

    @Autowired
    private final ParkingRepository parkingRepository;

    @Autowired
    private final CarRepository carRepository;

    @Autowired
    private final SlotRepository slotRepository;

    public ParkingService(ParkingRepository parkingRepository, CarRepository carRepository, SlotRepository slotRepository) {
        this.parkingRepository = parkingRepository;
        this.carRepository = carRepository;
        this.slotRepository = slotRepository;
    }

    public List<Parking> getAllParkings() {
        return parkingRepository.findAll();
    }

    public Optional<Parking> clientHistory(Long clientID) {
        return parkingRepository.findParkingsByCar_Client_Id(clientID);
    }

    public Optional<Parking> carHistory(Long carID) {
        return parkingRepository.findParkingsByCar_Id(carID);
    }

    @Transactional
    public void addParking(Parking parking) throws VIPSlotException {
        Car car = parking.getCar();
        Slot slot = parking.getSlot();
        if (slot.isIsVIP() && !car.getClient().isIsVIP())
            throw new VIPSlotException();
        car.setIsParked(true);
        slot.setIsAVAILABLE(false);
        parkingRepository.save(parking);
    }

    public void deleteParkings() {
        parkingRepository.deleteAll();
    }

}
