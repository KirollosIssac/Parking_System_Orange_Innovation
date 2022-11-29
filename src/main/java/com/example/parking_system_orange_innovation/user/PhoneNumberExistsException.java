package com.example.parking_system_orange_innovation.user;

public class PhoneNumberExistsException extends Exception{
    public PhoneNumberExistsException() {
        super("Phone number already exists!");
    }
}
