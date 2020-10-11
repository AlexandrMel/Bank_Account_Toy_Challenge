package com.example.Bank_Account_Toy.io.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "transaction_history")
public class TransactionHistoryEntity implements Serializable {
    private static final long serialVersionUID = -5978700523497134114L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String transactionType;
    @Column(nullable = false)
    private String transactionId;
    @Column(nullable = false)
    private Date createdAt;
    @Column(nullable = false, length = 35)
    private String iban;
    @Column(nullable = false, length = 2)
    private String ibanPrefix;
    @Column(nullable = false, columnDefinition = "DECIMAL(7,2) default '0.00'")
    private BigDecimal transactionAmount = BigDecimal.ZERO;
    @Column(nullable = false)
    private String ibanSender_Receiver;
    @Column(nullable = false, length = 2)
    private String ibanPrefix_Sender_Receiver;
    @Column(nullable = false)
    private String transactionPurpose;
    @Column(nullable = false, columnDefinition = "DECIMAL(7,2) default '0.00'")
    private BigDecimal balanceAfterTransaction;

    public BigDecimal getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public void setBalanceAfterTransaction(BigDecimal balanceAfterTransaction) {
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public String getIbanPrefix_Sender_Receiver() {
        return ibanPrefix_Sender_Receiver;
    }

    public void setIbanPrefix_Sender_Receiver(String ibanPrefix_Sender_Receiver) {
        this.ibanPrefix_Sender_Receiver = ibanPrefix_Sender_Receiver;
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

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getIbanSender_Receiver() {
        return ibanSender_Receiver;
    }

    public void setIbanSender_Receiver(String ibanSender_Receiver) {
        this.ibanSender_Receiver = ibanSender_Receiver;
    }

    public String getTransactionPurpose() {
        return transactionPurpose;
    }

    public void setTransactionPurpose(String transactionPurpose) {
        this.transactionPurpose = transactionPurpose;
    }
}
