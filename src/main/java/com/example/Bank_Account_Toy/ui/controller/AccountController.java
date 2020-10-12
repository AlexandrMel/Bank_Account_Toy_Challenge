package com.example.Bank_Account_Toy.ui.controller;

import com.example.Bank_Account_Toy.exceptions.AccountServiceException;
import com.example.Bank_Account_Toy.service.AccountService;
import com.example.Bank_Account_Toy.shared.dto.AccountDto;
import com.example.Bank_Account_Toy.ui.model.request.AccountDepositRequestModel;
import com.example.Bank_Account_Toy.ui.model.request.CreateAccountRequestModel;
import com.example.Bank_Account_Toy.ui.model.request.AccountTransferRequestModel;
import com.example.Bank_Account_Toy.ui.model.request.SetAccountSettingsRequestModel;
import com.example.Bank_Account_Toy.ui.model.response.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/accounts") //http://localhost:8080/accounts
public class AccountController {


    //Dependencies injection
    @Autowired
    AccountService accountService;

    //Route to create a new Account
    @PostMapping //http://localhost:8080/accounts
    public AccountDetailsResponseModel createAccount(@RequestBody CreateAccountRequestModel accountDetails) throws Exception {
        //Simple validation
        if (accountDetails.validator()) {
            throw new AccountServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
        if (accountDetails.getAccountType().equals("savings") ||
                accountDetails.getAccountType().equals("checking") ||
                accountDetails.getAccountType().equals("privateLoan")) {
            //Instantiate Model for return value
            AccountDetailsResponseModel returnValue = new AccountDetailsResponseModel();
            //Instantiate Data Transfer Object
            AccountDto accountDto = new AccountDto();
            BeanUtils.copyProperties(accountDetails, accountDto);
            //Call Create Account Service Layer Method
            AccountDto createdAccount = accountService.createAccount(accountDto);
            BeanUtils.copyProperties(createdAccount, returnValue);

            return returnValue;
        } else {
            throw new AccountServiceException(ErrorMessages.INVALID_ACCOUNT_TYPE.getErrorMessage());
        }
    }

    //Route to make a deposit into a specific Account
    @PutMapping(path = "/balance") // http://localhost:8080/accounts/balance
    public AccountBalanceResponseModel accountDeposit(@Valid @RequestBody AccountDepositRequestModel balanceDetails) throws Exception {
        if (balanceDetails.validator()) {
            throw new AccountServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
        //Create an instance of Data Transfer Object
        AccountDto accountDto = new AccountDto();
        //Copy the data from the request Body to the Data Transfer Object
        BeanUtils.copyProperties(balanceDetails, accountDto);
        //Pass the data to Service layer and call the necessary method to be executed and receive back the response from the database;
        AccountDto returnedUpdatedIban = accountService.accountDeposit(accountDto);
        //Create a response Object to be passed to User based on a specific Model(AccountBalanceModel)
        AccountBalanceResponseModel returnValue = new AccountBalanceResponseModel();
        //Copy the the data received from the Service Layer method into response Model and return to User
        BeanUtils.copyProperties(returnedUpdatedIban, returnValue);

        return returnValue;
    }


    //Get All Accounts that match a specific type
    @GetMapping(path = "/type/{type}")  // ex: http://localhost:8080/accounts/type/checking
    public List<AccountsByTypeResponseModel> getAccountsByType(@PathVariable String type) {
        //Instantiate response Model of List type
        List<AccountsByTypeResponseModel> returnValue = new ArrayList<AccountsByTypeResponseModel>();
        //Get The data from Service Layer using the custom created method
        List<AccountDto> accountDtoList = accountService.getAccountsByType(type);
        //Looping through the list got from Service layer and converting it to a list of response model
        for (AccountDto accountDto : accountDtoList) {
            AccountsByTypeResponseModel accountRest = new AccountsByTypeResponseModel();
            BeanUtils.copyProperties(accountDto, accountRest);
            returnValue.add(accountRest);
        }
        return returnValue;
    }

    // Get balance of a specific account by IBAN
    @GetMapping(path = "/balance/{iban}")// ex: http://localhost:8080/accounts/balance/{iban}
    public AccountBalanceResponseModel getAccountsBalance(@PathVariable String iban) {
        //Instantiate response model
        AccountBalanceResponseModel returnValue = new AccountBalanceResponseModel();
        //Getting data from service layer
        AccountDto accountDto = accountService.getAccountsBalance(iban);
        //copy data to response model
        BeanUtils.copyProperties(accountDto, returnValue);

        return returnValue;
    }

    //Set account settings, in particular lock/unlock account setting
    @PutMapping(path = "/settings")// ex: http://localhost:8080/accounts/settings
    public AccountSettingUpdateResponseModel setAccountSettings(@RequestBody SetAccountSettingsRequestModel settingDetails) throws Exception {
        //Simple validation
        if (settingDetails.validator()) {
            throw new AccountServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
        //Instantiate DTO Object
        AccountDto accountDto = new AccountDto();
        //Copy request body into DTO
        BeanUtils.copyProperties(settingDetails, accountDto);
        //Call the correspondent method in Service Layer
        AccountDto returnedUpdatedIban = accountService.setAccountSetting(accountDto);
        //Instantiate and return response model
        AccountSettingUpdateResponseModel returnValue = new AccountSettingUpdateResponseModel();
        BeanUtils.copyProperties(returnedUpdatedIban, returnValue);
        return returnValue;
    }

    //Make a transfer of funds from one account to another
    @PostMapping(path = "/transaction") // ex: http://localhost:8080/accounts/transaction
    public String accountTransfer(@RequestBody AccountTransferRequestModel transferDetails) throws Exception {
        //Simple validation
        if (transferDetails.validator()) {
            throw new AccountServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
        AccountDto accountDto = new AccountDto();
        //Copy request body into DTO
        BeanUtils.copyProperties(transferDetails, accountDto);
        //Call the correspondent method in Service Layer
        String transferSuccess = accountService.accountTransfer(accountDto);

        return transferSuccess;
    }

    //Get All Account transactions that match a specific iban
    @GetMapping(path = "/transaction/{iban}") // ex: http://localhost:8080/accounts/transaction/{iban}
    public List<TransactionHistoryResponseModel> getAccountTransactionHistoryByIban(@PathVariable String iban) {
        //Instantiate response Model of List type
        List<TransactionHistoryResponseModel> returnValue = new ArrayList<TransactionHistoryResponseModel>();
        //Get The data from Service Layer using the custom created method
        List<AccountDto> accountDtoList = accountService.getTransactionHistoryByIban(iban);
        //Looping through the list got from Service layer and converting it to a list of response model
        for (AccountDto accountDto : accountDtoList) {
            TransactionHistoryResponseModel accountRest = new TransactionHistoryResponseModel();
            BeanUtils.copyProperties(accountDto, accountRest);
            returnValue.add(accountRest);
        }
        return returnValue;
    }
}

