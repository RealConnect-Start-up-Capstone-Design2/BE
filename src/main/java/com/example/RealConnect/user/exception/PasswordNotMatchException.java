package com.example.RealConnect.user.exception;

public class PasswordNotMatchException extends RuntimeException {
    public PasswordNotMatchException(String message)
    {
        super(message);
    }
}
