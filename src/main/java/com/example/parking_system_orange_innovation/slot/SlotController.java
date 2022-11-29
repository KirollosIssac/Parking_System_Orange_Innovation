package com.example.parking_system_orange_innovation.slot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Slot>> getAllSlots() {
        return new ResponseEntity<>(slotService.getSlots(), HttpStatus.OK);
    }

    @GetMapping("/getAvailableSlots")
    public ResponseEntity<List<Slot>> getAvailableSlots() {
        return new ResponseEntity<>(slotService.getAvailableSlots(), HttpStatus.OK);
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
        slotService.updateSlot(slot);
        return new ResponseEntity<>("Slot updated successfully!", HttpStatus.OK);
    }

    @PutMapping("/assignSlot/{slotId}/{carId}")
    public ResponseEntity<String> assignSlot(@PathVariable("slotId") Long slotId, @PathVariable("carId") Long carId) throws VIPSlotException, CarNotFoundException {
        try {
            slotService.assignSlot(slotId, carId);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Slot assigned successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/deleteSlot/{slotId}")
    public ResponseEntity<String> deleteSlot(@PathVariable("slotId") Long slotId) {
        slotService.deleteSlot(slotId);
        return new ResponseEntity<>("Slot deleted successfully!", HttpStatus.OK);
    }

}
