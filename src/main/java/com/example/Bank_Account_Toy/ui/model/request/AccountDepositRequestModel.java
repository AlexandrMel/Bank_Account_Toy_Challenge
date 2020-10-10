package com.example.Bank_Account_Toy.ui.model.request;

import java.math.BigDecimal;

public class AccountDepositRequestModel {
    private String userId;
    private String iban;
    private BigDecimal balance;

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
