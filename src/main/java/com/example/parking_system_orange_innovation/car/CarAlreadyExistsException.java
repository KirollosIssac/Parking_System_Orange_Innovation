package com.example.parking_system_orange_innovation.car;

public class CarAlreadyExistsException extends Exception{

    public CarAlreadyExistsException() {
        super("Car already exists!");
    }

}
