package com.example.Bank_Account_Toy.shared;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class Utils {
    // Helper methods
    private final Random RANDOM = new SecureRandom();
    private final String NUMBERS = "0123456789";
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    // public version of the helper method to generate new IBAN
    public String generateIBAN(int length, String type) {
        return generateRandomNumberString(length, type);
    }

    // private version of the helper method to generate new IBAN
    private String generateRandomNumberString(int length, String type) {
        StringBuilder returnValue = new StringBuilder(length);
        String BASE = type == "mix" ? ALPHABET : NUMBERS;
        for (int i = 0; i < length; i++) {
            returnValue.append(BASE.charAt(RANDOM.nextInt(BASE.length())));
        }
        return new String(returnValue);
    }

    //public version of method to generate a set of transaction rules based on the account type
    public String setAccountTransactionRules(String accountType) {
        return generateAccountTransactionRules(accountType);
    }

    //private version of method to generate a set of transaction rules based on the account type
    private String generateAccountTransactionRules(String accountTape) {

        switch (accountTape) {
            case "checking":
                String returnChecking = "checking, savings, privateLoan";
                return returnChecking;
            case "savings":
                String returnSavings = "checking";
                return returnSavings;
            case "privateLoan":
                String returnPrivateLoan = "";
                return returnPrivateLoan;
        }
        return null;
    }
}
