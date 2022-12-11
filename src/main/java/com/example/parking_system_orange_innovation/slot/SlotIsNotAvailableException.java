package com.example.parking_system_orange_innovation.slot;

public class SlotIsNotAvailableException extends Exception{
    public SlotIsNotAvailableException() {
        super("Slot is not available!");
    }
}
