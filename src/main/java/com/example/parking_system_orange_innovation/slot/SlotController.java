package com.example.parking_system_orange_innovation.slot;

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

    @GetMapping("/getAvailableSlots")
    public List<Slot> getAvailableSlots() {
        return slotService.getAvailableSlots();
    }

    @PostMapping("/addSlot")
    public Slot addSlot(@RequestBody Slot slot) {
        return slotService.addSlot(slot);
    }

    @PutMapping("/freeSlots")
    public void freeSlots() {
        slotService.freeSlots();
    }

    @PutMapping("/updateSlot")
    public Optional<Slot> updateSlots(@RequestBody Slot slot) {
        return slotService.updateSlot(slot);
    }

    @PutMapping("/assignSlot/{slotId}/{carId}")
    public Optional<Slot> assignSlot(@PathVariable("slotId") Long slotId, @PathVariable("carId") Long carId) throws VIPSlotException, CarNotFoundException {
        return slotService.assignSlot(slotId, carId);
    }

    @DeleteMapping("/deleteSlot/{slotId}")
    public void deleteSlot(@PathVariable("slotId") Long slotId) {
        slotService.deleteSlot(slotId);
    }

}
