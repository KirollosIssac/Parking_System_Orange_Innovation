package com.example.parking_system_orange_innovation.user;

public class CarIsNotAssignedToClient extends Exception{
    public CarIsNotAssignedToClient() {
        super("Car is not assigned to client!");
    }
}
