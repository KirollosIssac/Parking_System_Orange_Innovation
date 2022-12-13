package com.example.parking_system_orange_innovation.slot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "slots")
public class SlotController {

    @Autowired
    private final SlotService slotService;

    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    @GetMapping("/getSlots")
    public ResponseEntity<List<Slot>> getAllSlots() {
        return new ResponseEntity<>(slotService.getSlots(), HttpStatus.OK);
    }

    @GetMapping("/getAvailableSlots")
    public ResponseEntity<List<Slot>> getAvailableSlots() {
        return new ResponseEntity<>(slotService.getAvailableSlots(), HttpStatus.OK);
    }

    @GetMapping("/getAvailableVIPSlots")
    public ResponseEntity<List<Slot>> getAvailableVIPSlots() {
        return new ResponseEntity<>(slotService.getAvailableVIPSlots(), HttpStatus.OK);
    }

    @PostMapping("/addSlot")
    public ResponseEntity<String> addSlot(@RequestBody Slot slot) {
        slotService.addSlot(slot);
        return new ResponseEntity<>("Slot added successfully!", HttpStatus.OK);
    }

    @PutMapping("/freeSlots")
    public ResponseEntity<String> freeSlots() {
        slotService.freeSlots();
        return new ResponseEntity<>("Slots are free!", HttpStatus.OK);
    }

    @PutMapping("/updateSlot")
    public ResponseEntity<String> updateSlots(@RequestBody Slot slot) {
        try {
            slotService.updateSlot(slot);
            return new ResponseEntity("Slot updated successfully!", HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/assignSlot/{slotId}/{carId}")
    public ResponseEntity<String> assignSlot(@PathVariable("slotId") Long slotId, @PathVariable("carId") Long carId) throws VIPSlotException, CarNotFoundException {
        try {
            slotService.assignSlot(slotId, carId);
            return new ResponseEntity<>("Slot assigned successfully!", HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/slotActivation/{slotId}/{isActive}")
    public ResponseEntity<String> deactivateSlot(@PathVariable("slotId") Long slotId, @PathVariable("isActive") Boolean isActive) {
        try {
            slotService.slotActivation(slotId, isActive);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }
        if (isActive)
            return new ResponseEntity<>("Slot activated successfully!", HttpStatus.OK);
        else
            return new ResponseEntity<>("Slot deactivated successfully!", HttpStatus.OK);
    }

}
