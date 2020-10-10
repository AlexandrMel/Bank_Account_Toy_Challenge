package com.example.Bank_Account_Toy.ui.model.request;

import java.math.BigDecimal;

public class AccountTransferRequestModel {
    private String userId;
    private String transferFromIban;
    private String transferToIban;
    private BigDecimal transferAmount;
    private String transferPurpose;

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

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getTransferPurpose() {
        return transferPurpose;
    }

    public void setTransferPurpose(String transferPurpose) {
        this.transferPurpose = transferPurpose;
    }
}
