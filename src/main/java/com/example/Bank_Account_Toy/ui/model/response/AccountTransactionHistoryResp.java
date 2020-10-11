package com.example.Bank_Account_Toy.ui.model.response;

import java.math.BigDecimal;
import java.util.Date;

public class AccountTransactionHistoryResp {
    private String transactionPurpose;
    private BigDecimal transactionAmount;
    private String transactionType;
    private String transactionId;
    private Date createdAt;
    private String ibanSender_Receiver;
    private String ibanPrefix_Sender_Receiver;
    private BigDecimal balanceAfterTransaction;

    public String getTransactionPurpose() {
        return transactionPurpose;
    }

    public void setTransactionPurpose(String transactionPurpose) {
        this.transactionPurpose = transactionPurpose;
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

    public BigDecimal getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public void setBalanceAfterTransaction(BigDecimal balanceAfterTransaction) {
        this.balanceAfterTransaction = balanceAfterTransaction;
    }
}
