package com.example.parking_system_orange_innovation.car;

public class CarIsNotAssignedToClientException extends Exception{
    public CarIsNotAssignedToClientException() {
        super("This car is not assigned to any client!");
    }
}
