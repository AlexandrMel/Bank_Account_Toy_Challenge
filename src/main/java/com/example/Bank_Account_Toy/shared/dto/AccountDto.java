package com.example.Bank_Account_Toy.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//Main Data Transfer Object, it is huge, but does the job
public class AccountDto implements Serializable {
    private static final long serialVersionUID = -820594852955351035L;
    private String firstName;
    private String lastName;
    private String accountType;
    private String iban;
    private String ibanPrefix;
    private BigDecimal balance = BigDecimal.ZERO;
    private Boolean locked = false;
    private String userId;
    private String sendMoneyTo;
    private String settingId;
    private Boolean settingValue;
    private String transferToIbanPrefix;
    private String transferToIban;
    private String transactionPurpose;
    private BigDecimal transactionAmount;
    private String transactionType;
    private String transactionId;
    private Date createdAt;
    private String ibanSender_Receiver;
    private String ibanPrefix_Sender_Receiver;
    private BigDecimal balanceAfterTransaction;

    public BigDecimal getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public void setBalanceAfterTransaction(BigDecimal balanceAfterTransaction) {
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getIbanSender_Receiver() {
        return ibanSender_Receiver;
    }

    public void setIbanSender_Receiver(String ibanSender_Receiver) {
        this.ibanSender_Receiver = ibanSender_Receiver;
    }

    public String getIbanPrefix_Sender_Receiver() {
        return ibanPrefix_Sender_Receiver;
    }

    public void setIbanPrefix_Sender_Receiver(String ibanPrefix_Sender_Receiver) {
        this.ibanPrefix_Sender_Receiver = ibanPrefix_Sender_Receiver;
    }

    public String getTransferToIbanPrefix() {
        return transferToIbanPrefix;
    }

    public void setTransferToIbanPrefix(String transferToIbanPrefix) {
        this.transferToIbanPrefix = transferToIbanPrefix;
    }

    public String getTransactionPurpose() {
        return transactionPurpose;
    }

    public void setTransactionPurpose(String transactionPurpose) {
        this.transactionPurpose = transactionPurpose;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransferToIban() {
        return transferToIban;
    }

    public void setTransferToIban(String transferToIban) {
        this.transferToIban = transferToIban;
    }

    public String getSettingId() {
        return settingId;
    }

    public void setSettingId(String settingId) {
        this.settingId = settingId;
    }

    public Boolean getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(Boolean settingValue) {
        this.settingValue = settingValue;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSendMoneyTo() {
        return sendMoneyTo;
    }

    public void setSendMoneyTo(String sendMoneyTo) {
        this.sendMoneyTo = sendMoneyTo;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getIbanPrefix() {
        return ibanPrefix;
    }

    public void setIbanPrefix(String ibanPrefix) {
        this.ibanPrefix = ibanPrefix;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
}
