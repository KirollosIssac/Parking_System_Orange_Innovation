package com.example.parking_system_orange_innovation.user;

public class ClientDoNotHaveCarException extends Exception{
    public ClientDoNotHaveCarException() {
        super("This client don't have a car");
    }
}
