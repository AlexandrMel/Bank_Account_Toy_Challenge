package com.example.Bank_Account_Toy.ui.model.request;

import static java.util.Objects.isNull;

public class AccountDetailsRequestModel {
    private String firstName = "";
    private String lastName = "";
    private String accountType = "";
    private String userId = "";

    public Boolean validator() {
        return nullOrEmpty(firstName) ||
                nullOrEmpty(lastName) ||
                nullOrEmpty(accountType) ||
                nullOrEmpty(userId);
    }

    public Boolean nullOrEmpty(String prop) {
        return prop.isEmpty() || isNull(prop);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
