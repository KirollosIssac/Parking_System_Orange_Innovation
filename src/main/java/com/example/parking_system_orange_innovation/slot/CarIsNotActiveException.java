package com.example.parking_system_orange_innovation.slot;

public class CarIsNotActiveException extends Exception{
    public CarIsNotActiveException() {
        super("Car is inactive!");
    }
}
