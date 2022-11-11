package com.example.parking_system_orange_innovation.user;

public class EmailExistsException extends Exception{

    public EmailExistsException() {
        super("This email address already signed up!");
    }
}
