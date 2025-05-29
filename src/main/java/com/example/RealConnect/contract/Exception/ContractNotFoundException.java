package com.example.RealConnect.contract.Exception;

public class ContractNotFoundException extends RuntimeException {
    public ContractNotFoundException(String msg)
    {
        super(msg);
    }
}
