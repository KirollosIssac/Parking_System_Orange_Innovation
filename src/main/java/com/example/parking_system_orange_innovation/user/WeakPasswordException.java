package com.example.parking_system_orange_innovation.user;

public class WeakPasswordException extends Exception{

    public WeakPasswordException() {
        super("Your password is weak! Try another one");
    }
}
