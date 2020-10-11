package com.example.Bank_Account_Toy.ui.model.request;

import java.math.BigDecimal;

import static java.util.Objects.isNull;

public class AccountTransferRequestModel {
    private String userId = "";
    private String ibanPrefix = "";
    private String iban = "";
    private String transferToIbanPrefix = "";
    private String transferToIban = "";
    private BigDecimal transactionAmount;
    private String transactionPurpose = "";
    private String transactionType = "";

    public Boolean validator() {
        return nullOrEmpty(iban) ||
                nullOrEmpty(ibanPrefix) ||
                nullOrEmpty(userId) ||
                nullOrEmpty(transactionType) ||
                nullOrEmpty(transactionPurpose) ||
                isNull(transactionAmount) ||
                nullOrEmpty(transferToIban) ||
                nullOrEmpty(transferToIbanPrefix);

    }

    public Boolean nullOrEmpty(String prop) {
        return prop.isEmpty() || isNull(prop);
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionPurpose() {
        return transactionPurpose;
    }

    public void setTransactionPurpose(String transactionPurpose) {
        this.transactionPurpose = transactionPurpose;
    }

    public String getIbanPrefix() {
        return ibanPrefix;
    }

    public void setIbanPrefix(String ibanPrefix) {
        this.ibanPrefix = ibanPrefix;
    }

    public String getTransferToIbanPrefix() {
        return transferToIbanPrefix;
    }

    public void setTransferToIbanPrefix(String transferToIbanPrefix) {
        this.transferToIbanPrefix = transferToIbanPrefix;
    }

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

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getTransferToIban() {
        return transferToIban;
    }

    public void setTransferToIban(String transferToIban) {
        this.transferToIban = transferToIban;
    }

}
