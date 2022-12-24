package com.example.parking_system_orange_innovation.slot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Slot>> getAllSlots() {
        return new ResponseEntity<>(slotService.getSlots(), HttpStatus.OK);
    }

    @GetMapping("/getAvailableSlots")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public ResponseEntity<List<Slot>> getAvailableSlots() {
        return new ResponseEntity<>(slotService.getAvailableSlots(), HttpStatus.OK);
    }

    @GetMapping("/getAvailableVIPSlots")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public ResponseEntity<List<Slot>> getAvailableVIPSlots() {
        return new ResponseEntity<>(slotService.getAvailableVIPSlots(), HttpStatus.OK);
    }

    @PostMapping("/addSlot")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addSlot(@RequestBody Slot slot) {
        slotService.addSlot(slot);
        return new ResponseEntity<>("Slot added successfully!", HttpStatus.OK);
    }

    @PutMapping("/freeSlots")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> freeSlots() {
        slotService.freeSlots();
        return new ResponseEntity<>("Slots are free!", HttpStatus.OK);
    }

    @PutMapping("/updateSlot")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> updateSlots(@RequestBody Slot slot) {
        try {
            slotService.updateSlot(slot);
            return new ResponseEntity("Slot updated successfully!", HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/assignSlot/{slotId}/{carId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> assignSlot(@PathVariable("slotId") Long slotId, @PathVariable("carId") Long carId) throws VIPSlotException, CarNotFoundException {
        try {
            slotService.assignSlot(slotId, carId);
            return new ResponseEntity<>("Slot assigned successfully!", HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/slotActivation/{slotId}/{isActive}")
    @PreAuthorize("hasAuthority('ADMIN')")
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
