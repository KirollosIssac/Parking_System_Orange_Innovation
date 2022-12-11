package com.example.parking_system_orange_innovation.car;

public class CarIsCurrentlyParkedException extends Exception{
    public CarIsCurrentlyParkedException() {
        super("Car is currently parked!");
    }
}
