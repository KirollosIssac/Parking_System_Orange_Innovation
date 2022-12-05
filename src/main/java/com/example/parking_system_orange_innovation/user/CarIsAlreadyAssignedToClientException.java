package com.example.parking_system_orange_innovation.user;

public class CarIsAlreadyAssignedToClientException extends Exception{
    public CarIsAlreadyAssignedToClientException() {
        super("Car is already assigned to client!");
    }
}
