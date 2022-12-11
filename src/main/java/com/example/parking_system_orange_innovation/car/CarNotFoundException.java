package com.example.parking_system_orange_innovation.car;

public class CarNotFoundException extends Exception{
    public CarNotFoundException() {
        super("Car is not found!");
    }
}
