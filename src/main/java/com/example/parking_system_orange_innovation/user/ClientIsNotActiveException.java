package com.example.parking_system_orange_innovation.user;

public class ClientIsNotActiveException extends Exception{
    public ClientIsNotActiveException() {
        super("Client is inactive!");
    }
}
