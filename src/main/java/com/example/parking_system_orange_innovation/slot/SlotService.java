package com.example.parking_system_orange_innovation.slot;

import com.example.parking_system_orange_innovation.car.Car;
import com.example.parking_system_orange_innovation.car.CarRepository;
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
        return slotRepository.findSlotsByIsAvailableIsTrue();
    }

    public Slot addSlot(Slot slot) {
        slotRepository.save(slot);
        return slot;
    }

    @Transactional
    public void freeSlots() {
        List<Slot> slots = slotRepository.findAll();
        for (Slot slot: slots) {
            slot.setIsAvailable(true);
        }
    }

    @Transactional
    public void freeSlot(Long slotId) {
        Optional<Slot> slot = slotRepository.findById(slotId);
        slot.get().getCar().setIsParked(false);
        slot.get().setCar(null);
        slot.get().setIsAvailable(true);
    }

    @Transactional
    public Optional<Slot> assignSlot(Long slotId, Long carId) throws CarNotFoundException, VIPSlotException {
        Optional<Car> optionalCar = carRepository.findById(carId);
        Optional<Slot> optionalSlot = slotRepository.findById(slotId);
        if (!optionalCar.isPresent())
            throw new CarNotFoundException();
        if (!clientService.getCarOwner(carId).get().getIsVIP() && optionalSlot.get().getIsVIP())
            throw new VIPSlotException();
        optionalSlot.get().setCar(optionalCar.get());
        optionalSlot.get().setIsAvailable(false);
        optionalCar.get().setIsParked(true);
        return optionalSlot;
    }

    @Transactional
    public Optional<Slot> updateSlot(Slot slot) {
        Optional<Slot> optionalSlot = slotRepository.findById(slot.getId());
        optionalSlot.get().setIsAvailable(slot.getIsAvailable());
        optionalSlot.get().setIsVIP(slot.getIsVIP());
        return optionalSlot;
    }

    @Transactional
    public void deleteSlot(Long slotId) {
        Optional<Slot> optionalSlot = slotRepository.findById(slotId);
        if(optionalSlot.get().getCar() != null)
            optionalSlot.get().getCar().setIsParked(false);
        slotRepository.deleteById(slotId);
    }

}