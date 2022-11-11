package com.example.parking_system_orange_innovation.slot;

import com.example.parking_system_orange_innovation.car.Car;
import com.example.parking_system_orange_innovation.car.CarRepository;
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

    public SlotService(SlotRepository slotRepository, CarRepository carRepository) {
        this.slotRepository = slotRepository;
        this.carRepository = carRepository;
    }

    public List<Slot> getSlots() {
        return slotRepository.findAll();
    }

    /*public List<Slot> getAvailableSlots() {
        return slotRepository.findSlotsByIsAVAILABLEEquals(true);
    }*/

    public Slot addSlot(Slot slot) {
        slotRepository.save(slot);
        return slot;
    }

    @Transactional
    public void freeSlots() {
        List<Slot> slots = slotRepository.findAll();
        for (Slot slot: slots) {
            slot.setIsAVAILABLE(true);
        }
    }

    @Transactional
    public Slot assignSlot(Slot slot, Car car) throws CarNotFoundException {
        Optional<Car> optionalCar = carRepository.findById(car.getId());
        if (!optionalCar.isPresent())
            throw new CarNotFoundException();
        slot.setCar(car);
        slot.setIsAVAILABLE(false);
        return slot;
    }

    @Transactional
    public Optional<Slot> updateSlot(Long slotID, Slot slot) {
        Optional<Slot> optionalSlot = slotRepository.findById(slotID);
        optionalSlot.get().setIsAVAILABLE(slot.isIsAVAILABLE());
        optionalSlot.get().setIsVIP(slot.isIsVIP());
        return optionalSlot;
    }

}