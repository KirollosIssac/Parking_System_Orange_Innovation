package com.example.parking_system_orange_innovation.slot;

import com.example.parking_system_orange_innovation.car.Car;
import com.example.parking_system_orange_innovation.car.CarRepository;
import com.example.parking_system_orange_innovation.user.CarIsNotAssignedToClient;
import com.example.parking_system_orange_innovation.user.ClientService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class SlotService {

    @Autowired
    private final SlotRepository slotRepository;

    @Autowired
    private final CarRepository carRepository;

    @Autowired
    private final ClientService clientService;

    public SlotService(SlotRepository slotRepository, CarRepository carRepository, ClientService clientService) {
        this.slotRepository = slotRepository;
        this.carRepository = carRepository;
        this.clientService = clientService;
    }

    public List<Slot> getSlots() {
        return slotRepository.findAll();
    }

    public Optional<Slot> getSlot(Long id) {
        return slotRepository.findById(id);
    }

    public List<Slot> getAvailableSlots() {
        return slotRepository.findSlotsByIsAvailableIsTrueAndIsActiveIsTrueAndIsVIPIsFalse();
    }

    public List<Slot> getAvailableVIPSlots() {
        return slotRepository.findSlotsByIsAvailableIsTrueAndIsActiveIsTrueAndIsVIPIsTrue();
    }

    public Slot addSlot(Slot slot) {
        slotRepository.save(slot);
        return slot;
    }

    @Transactional
    public void freeSlots() {
        List<Slot> slots = slotRepository.findAll();
        for (Slot slot: slots) {
            if (slot.getIsActive()) slot.setIsAvailable(true);
        }
    }

    @Transactional
    public void freeSlot(Long slotId) throws SlotIsNotActiveException {
        Optional<Slot> slot = slotRepository.findById(slotId);
        if (!slot.get().getIsActive())
            throw new SlotIsNotActiveException();
        slot.get().getCar().setIsParked(false);
        slot.get().setCar(null);
        slot.get().setIsAvailable(true);
    }

    @Transactional
    public Optional<Slot> assignSlot(Long slotId, Long carId) throws CarNotFoundException, VIPSlotException, CarIsNotActiveException, SlotIsNotActiveException, CarIsNotAssignedToClient {
        Optional<Car> optionalCar = carRepository.findById(carId);
        if (!optionalCar.isPresent())
            throw new CarNotFoundException();
        if (!optionalCar.get().getIsActive())
            throw new CarIsNotActiveException();
        Optional<Slot> optionalSlot = slotRepository.findById(slotId);
        if (!optionalSlot.get().getIsActive())
            throw new SlotIsNotActiveException();
        if (!clientService.getCarOwner(carId).get().getIsVIP() && optionalSlot.get().getIsVIP())
            throw new VIPSlotException();
        optionalSlot.get().setCar(optionalCar.get());
        optionalSlot.get().setIsAvailable(false);
        optionalCar.get().setIsParked(true);
        return optionalSlot;
    }

    @Transactional
    public void updateSlot(Slot slot) throws CarIsCurrentlyParkingHereException, CarIsNotActiveException {
        Optional<Slot> optionalSlot = slotRepository.findById(slot.getId());
        if (!optionalSlot.get().getIsActive())
            throw new CarIsNotActiveException();
        if (!optionalSlot.get().getIsAvailable())
            throw new CarIsCurrentlyParkingHereException();
        optionalSlot.get().setIsAvailable(slot.getIsAvailable());
        optionalSlot.get().setIsVIP(slot.getIsVIP());
    }

    @Transactional
    public void slotActivation(Long slotId, Boolean isActive) throws CarIsCurrentlyParkingHereException {
        Optional<Slot> optionalSlot = slotRepository.findById(slotId);
        if (isActive)
            optionalSlot.get().setIsActive(true);
        else {
            if (!optionalSlot.get().getIsAvailable())
                throw new CarIsCurrentlyParkingHereException();
            optionalSlot.get().setIsActive(false);
        }
    }

}