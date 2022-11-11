package com.example.parking_system_orange_innovation.user;

public class UserNameExistsException extends Exception{

    public UserNameExistsException() {
        super("User name exists! Try another one");
    }
}
