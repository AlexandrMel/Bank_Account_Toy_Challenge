package com.example.Bank_Account_Toy.ui.model.request;

import java.math.BigDecimal;

public class AccountTransferRequestModel {
    private String userId;
    private String transferFromIban;
    private String transferToIban;
    private BigDecimal transactionAmount;
    private String transferPurpose;

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTransferFromIban() {
        return transferFromIban;
    }

    public void setTransferFromIban(String transferFromIban) {
        this.transferFromIban = transferFromIban;
    }

    public String getTransferToIban() {
        return transferToIban;
    }

    public void setTransferToIban(String transferToIban) {
        this.transferToIban = transferToIban;
    }

    public String getTransferPurpose() {
        return transferPurpose;
    }

    public void setTransferPurpose(String transferPurpose) {
        this.transferPurpose = transferPurpose;
    }
}
