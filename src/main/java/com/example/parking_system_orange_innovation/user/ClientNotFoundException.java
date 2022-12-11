package com.example.parking_system_orange_innovation.user;

public class ClientNotFoundException extends Exception{
    public ClientNotFoundException() {
        super("Client is not found!");
    }
}
