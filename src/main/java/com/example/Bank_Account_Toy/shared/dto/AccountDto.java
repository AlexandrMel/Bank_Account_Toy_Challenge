package com.example.Bank_Account_Toy.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

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
    private String receiveMoneyFrom;
    private String sendMoneyTo;
    private String settingId;
    private Boolean settingValue;
    private String transferFromIban;
    private String transferToIban;
    private BigDecimal transferAmount;
    private String transferPurpose;

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

    public String getReceiveMoneyFrom() {
        return receiveMoneyFrom;
    }

    public void setReceiveMoneyFrom(String receiveMoneyFrom) {
        this.receiveMoneyFrom = receiveMoneyFrom;
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
