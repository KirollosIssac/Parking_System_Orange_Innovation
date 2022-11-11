package com.example.parking_system_orange_innovation.parking;

public class VIPSlotException extends Exception{

    public VIPSlotException() {
        super("This slot is for VIP clients.");
    }
}
