package com.example.parking_system_orange_innovation.user;

public class CarIsNotAssignedToThisClientException extends Exception{
    public CarIsNotAssignedToThisClientException() {
        super("This car is not assigned to this client!");
    }
}
