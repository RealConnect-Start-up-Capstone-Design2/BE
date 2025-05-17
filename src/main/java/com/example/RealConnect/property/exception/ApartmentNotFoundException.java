package com.example.RealConnect.property.exception;

public class ApartmentNotFoundException extends RuntimeException {
    public ApartmentNotFoundException(String msg)
    {
        super(msg);
    }
}
