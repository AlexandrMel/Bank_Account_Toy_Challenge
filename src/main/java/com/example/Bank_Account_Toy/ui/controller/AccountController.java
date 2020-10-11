package com.example.Bank_Account_Toy.ui.controller;


import com.example.Bank_Account_Toy.service.AccountService;
import com.example.Bank_Account_Toy.shared.dto.AccountDto;
import com.example.Bank_Account_Toy.ui.model.request.AccountDepositRequestModel;
import com.example.Bank_Account_Toy.ui.model.request.AccountDetailsRequestModel;
import com.example.Bank_Account_Toy.ui.model.request.AccountTransferRequestModel;
import com.example.Bank_Account_Toy.ui.model.request.SetAccountSettingsRequestModel;
import com.example.Bank_Account_Toy.ui.model.response.*;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/accounts") //http://localhost:8080/accounts
public class AccountController {

    @Autowired
    AccountService accountService;


    //Route to make a deposit into a specific Account
    @PutMapping(path = "/balance") // http://localhost:8080/accounts/balance
    public AccountBalanceModel accountDeposit(@RequestBody AccountDepositRequestModel balanceDetails) throws NotFoundException {
        //Create an instance of Data Transfer Object
        AccountDto accountDto = new AccountDto();
        //Copy the data from the request Body to the Data Transfer Object
        BeanUtils.copyProperties(balanceDetails, accountDto);
        //Pass the data to Service layer and call the necessary method to be executed and receive back the response from the database;
        AccountDto returnedUpdatedIban = accountService.accountDeposit(accountDto);
        //Create a response Object to be passed to User based on a specific Model(AccountBalanceModel)
        AccountBalanceModel returnValue = new AccountBalanceModel();
        //Copy the the data received from the Service Layer method into response Model and return to User
        BeanUtils.copyProperties(returnedUpdatedIban, returnValue);
        return returnValue;
    }

    @PostMapping
    public AccountRest createAccount(@RequestBody AccountDetailsRequestModel accountDetails) {

        AccountRest returnValue = new AccountRest();
        AccountDto accountDto = new AccountDto();
        BeanUtils.copyProperties(accountDetails, accountDto);
        AccountDto createdAccount = accountService.createAccount(accountDto);
        BeanUtils.copyProperties(createdAccount, returnValue);

        return returnValue;
    }

    //Get All Accounts that match a specific type
    @GetMapping(path = "/type/{type}")
    public List<AccountRespByType> getAccountsByType(@PathVariable String type) {
        List<AccountRespByType> returnValue = new ArrayList<AccountRespByType>();
        List<AccountDto> accountDtoList = accountService.getAccountsByType(type);
        for (AccountDto accountDto : accountDtoList) {
            AccountRespByType accountRest = new AccountRespByType();
            BeanUtils.copyProperties(accountDto, accountRest);
            returnValue.add(accountRest);
        }
        return returnValue;
    }


    @GetMapping(path = "/balance/{iban}")
    public AccountBalanceModel getAccountsBalance(@PathVariable String iban) {
        AccountBalanceModel returnValue = new AccountBalanceModel();

        AccountDto accountDto = accountService.getAccountsBalance(iban);
        BeanUtils.copyProperties(accountDto, returnValue);

        return returnValue;
    }


    @PutMapping(path = "/settings/{iban}")
    public AccountRespSettingUpdate setAccountSettings(@PathVariable String iban, @RequestBody SetAccountSettingsRequestModel settingDetails) {
        AccountDto accountDto = new AccountDto();
        BeanUtils.copyProperties(settingDetails, accountDto);

        AccountDto returnedUpdatedIban = accountService.setAccountSetting(iban, accountDto);
        AccountRespSettingUpdate returnValue = new AccountRespSettingUpdate();
        BeanUtils.copyProperties(returnedUpdatedIban, returnValue);
        return returnValue;
    }

    @PostMapping(path = "/transaction")
    public String accountTransfer(@RequestBody AccountTransferRequestModel transferDetails) {

        //AccountTransferRequestModel returnValue = new AccountTransferRequestModel();
        AccountDto accountDto = new AccountDto();
        BeanUtils.copyProperties(transferDetails, accountDto);
        String transferSuccess = accountService.accountTransfer(accountDto);
        //BeanUtils.copyProperties(createdAccount, returnValue);

        return transferSuccess;
    }

    //Get All Account transactions that match a specific iban
    @GetMapping(path = "/transaction/{iban}")
    public List<AccountTransactionHistoryResp> getAccountTransactionHistoryByIban(@PathVariable String iban) {
        List<AccountTransactionHistoryResp> returnValue = new ArrayList<AccountTransactionHistoryResp>();
        List<AccountDto> accountDtoList = accountService.getTransactionHistoryByIban(iban);
        for (AccountDto accountDto : accountDtoList) {
            AccountTransactionHistoryResp accountRest = new AccountTransactionHistoryResp();
            BeanUtils.copyProperties(accountDto, accountRest);
            returnValue.add(accountRest);
        }
        return returnValue;
    }
}

