package com.example.parking_system_orange_innovation.slot;

public class CarIsCurrentlyParkingHereException extends Exception{
    public CarIsCurrentlyParkingHereException() {
        super("A car is currently parking here!");
    }
}
