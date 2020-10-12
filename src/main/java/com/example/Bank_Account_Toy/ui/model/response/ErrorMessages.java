package com.example.Bank_Account_Toy.ui.model.response;

//Enums with predefined Exception Messages for better Exception Handling
public enum ErrorMessages {

    MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields"),
    ACCOUNT_LOCKED("Unfortunately you cannot perform this transaction because your account is locked! "),
    COULD_NOT_CHANGE_SETTING("Unfortunately we could not update your setting, please try again"),
    NO_ACCOUNT_FOUND("Account with the provided IBAN is not found"),
    INSUFFICIENT_FUNDS("Your account has insufficient funds for the transfer"),
    COULD_NOT_UPDATE_BALANCE("Could not update your balance, please try again!"),
    COULD_NOT_UPDATE_TRANSACTION_HISTORY("An error occurred while updating your transaction history"),
    COULD_NOT_CREATE_NEW_ACCOUNT("An error occurred while trying to create your account, please try again"),
    UNABLE_TO_TRANSFER_TO_ACCOUNT("Your Account Type does not support transfer operation to this specific Account, please check your account settings"),
    INVALID_ACCOUNT_TYPE("Please provide a valid account type (checking, savings or privateLoan)");


    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}