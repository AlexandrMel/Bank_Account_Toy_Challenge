package com.example.Bank_Account_Toy.io.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity(name = "accounts")
public class AccountEntity implements Serializable {
    private static final long serialVersionUID = 260415159772477368L;


    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false, length = 35, unique = true)
    private String iban;
    @Column(nullable = false, length = 2)
    private String ibanPrefix;
    @Column(nullable = false, length = 50)
    private String firstName;
    @Column(nullable = false, columnDefinition = "DECIMAL(7,2) default '0.00'")
    private BigDecimal balance = BigDecimal.ZERO;
    @Column(nullable = false)
    private Boolean locked = false;
    @Column(nullable = false, length = 50)
    private String userId;
    @Column(nullable = false)
    private String ReceiveMoneyFrom;
    @Column(nullable = false)
    private String SendMoneyTo;


    public String getReceiveMoneyFrom() {
        return ReceiveMoneyFrom;
    }

    public void setReceiveMoneyFrom(String receiveMoneyFrom) {
        ReceiveMoneyFrom = receiveMoneyFrom;
    }

    public String getSendMoneyTo() {
        return SendMoneyTo;
    }

    public void setSendMoneyTo(String sendMoneyTo) {
        SendMoneyTo = sendMoneyTo;
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

    @Column(nullable = false, length = 50)
    private String lastName;
    @Column(nullable = false, length = 50)
    private String accountType;

    public String getIbanPrefix() {
        return ibanPrefix;
    }

    public void setIbanPrefix(String ibanPrefix) {
        this.ibanPrefix = ibanPrefix;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

}
