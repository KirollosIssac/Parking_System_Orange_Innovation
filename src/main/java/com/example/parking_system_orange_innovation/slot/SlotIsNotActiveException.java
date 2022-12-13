package com.example.parking_system_orange_innovation.slot;

public class SlotIsNotActiveException extends Exception{
    public SlotIsNotActiveException() {
        super("Slot is inactive!");
    }
}
