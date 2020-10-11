package com.example.Bank_Account_Toy.exceptions;

// Instantiating  Exception from RuntimeException
public class AccountServiceException extends RuntimeException {


    private static final long serialVersionUID = 209507288353319104L;

    public AccountServiceException(String message) {
        super(message);
    }
}

