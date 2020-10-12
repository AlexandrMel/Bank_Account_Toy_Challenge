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

    @Autowired
    AccountService accountService;


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

    @PostMapping
    public AccountDetailsResponseModel createAccount(@RequestBody CreateAccountRequestModel accountDetails) throws Exception {
        //Simple validation
        if (accountDetails.validator()) {
            throw new AccountServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
        if (accountDetails.getAccountType().equals("savings") || accountDetails.getAccountType().equals("checking") || accountDetails.getAccountType().equals("privateLoan")) {
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

    //Get All Accounts that match a specific type
    @GetMapping(path = "/type/{type}")
    public List<AccountsByTypeResponseModel> getAccountsByType(@PathVariable String type) {
        List<AccountsByTypeResponseModel> returnValue = new ArrayList<AccountsByTypeResponseModel>();
        List<AccountDto> accountDtoList = accountService.getAccountsByType(type);
        for (AccountDto accountDto : accountDtoList) {
            AccountsByTypeResponseModel accountRest = new AccountsByTypeResponseModel();
            BeanUtils.copyProperties(accountDto, accountRest);
            returnValue.add(accountRest);
        }
        return returnValue;
    }


    @GetMapping(path = "/balance/{iban}")
    public AccountBalanceResponseModel getAccountsBalance(@PathVariable String iban) {
        AccountBalanceResponseModel returnValue = new AccountBalanceResponseModel();

        AccountDto accountDto = accountService.getAccountsBalance(iban);
        BeanUtils.copyProperties(accountDto, returnValue);

        return returnValue;
    }


    @PutMapping(path = "/settings")
    public AccountSettingUpdateResponseModel setAccountSettings(@RequestBody SetAccountSettingsRequestModel settingDetails) {
        if (settingDetails.validator()) {
            throw new AccountServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

        AccountDto accountDto = new AccountDto();
        BeanUtils.copyProperties(settingDetails, accountDto);

        AccountDto returnedUpdatedIban = accountService.setAccountSetting(accountDto);
        AccountSettingUpdateResponseModel returnValue = new AccountSettingUpdateResponseModel();
        BeanUtils.copyProperties(returnedUpdatedIban, returnValue);
        return returnValue;
    }

    @PostMapping(path = "/transaction")
    public String accountTransfer(@RequestBody AccountTransferRequestModel transferDetails) throws Exception {
        if (transferDetails.validator()) {
            throw new AccountServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
        //AccountTransferRequestModel returnValue = new AccountTransferRequestModel();
        AccountDto accountDto = new AccountDto();
        BeanUtils.copyProperties(transferDetails, accountDto);
        String transferSuccess = accountService.accountTransfer(accountDto);
        //BeanUtils.copyProperties(createdAccount, returnValue);

        return transferSuccess;
    }

    //Get All Account transactions that match a specific iban
    @GetMapping(path = "/transaction/{iban}")
    public List<TransactionHistoryResponseModel> getAccountTransactionHistoryByIban(@PathVariable String iban) {
        List<TransactionHistoryResponseModel> returnValue = new ArrayList<TransactionHistoryResponseModel>();
        List<AccountDto> accountDtoList = accountService.getTransactionHistoryByIban(iban);
        for (AccountDto accountDto : accountDtoList) {
            TransactionHistoryResponseModel accountRest = new TransactionHistoryResponseModel();
            BeanUtils.copyProperties(accountDto, accountRest);
            returnValue.add(accountRest);
        }
        return returnValue;
    }
}

