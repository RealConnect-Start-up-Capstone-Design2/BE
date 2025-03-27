package com.example.RealConnect.user.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String message)
    {
        super(message);
    }
}
