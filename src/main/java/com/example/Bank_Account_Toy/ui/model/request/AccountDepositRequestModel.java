package com.example.Bank_Account_Toy.ui.model.request;

import static java.util.Objects.isNull;

import java.math.BigDecimal;

//Model to convert incoming Json data into Java Object for Account Deposit Route
public class AccountDepositRequestModel {

    private String transactionType = "";
    private String userId = "";
    private String ibanPrefix = "";
    private String iban = "";
    private BigDecimal transactionAmount;
    private String transactionPurpose = "";

    //Simple validator
    public Boolean validator() {
        return nullOrEmpty(iban) ||
                nullOrEmpty(ibanPrefix) ||
                nullOrEmpty(userId) ||
                nullOrEmpty(transactionType) ||
                nullOrEmpty(transactionPurpose) ||
                isNull(transactionAmount);

    }

    public Boolean nullOrEmpty(String prop) {
        return prop.isEmpty() || isNull(prop);
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

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
