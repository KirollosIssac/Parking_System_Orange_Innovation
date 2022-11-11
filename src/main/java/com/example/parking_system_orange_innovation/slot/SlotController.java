package com.example.parking_system_orange_innovation.slot;

import com.example.parking_system_orange_innovation.car.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "slots")
public class SlotController {

    @Autowired
    private final SlotService slotService;

    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    @GetMapping("/getSlots")
    public List<Slot> getAllSlots() {
        return slotService.getSlots();
    }

    /*@GetMapping("/getAvailableSlots")
    public List<Slot> getAvailableSlots() {
        return slotService.getAvailableSlots();
    }*/

    @PostMapping("/addSlot")
    public Slot addSlot(@RequestBody Slot slot) {
        return slotService.addSlot(slot);
    }

    @PutMapping("/assignSlot")
    public Slot assignSlot(@RequestBody Slot slot, @RequestBody Car car) throws CarNotFoundException{
        return slotService.assignSlot(slot, car);
    }

    @PutMapping("/freeSlots")
    public void freeSlots() {
        slotService.freeSlots();
    }

    @PutMapping("/updateSlots/{slotID}")
    public Optional<Slot> updateSlots(@PathVariable("slotID") Long slotID, @RequestBody Slot slot) {
        return slotService.updateSlot(slotID, slot);
    }

}
