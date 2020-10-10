package com.example.Bank_Account_Toy.ui.controller;


import com.example.Bank_Account_Toy.service.AccountService;
import com.example.Bank_Account_Toy.shared.dto.AccountDto;
import com.example.Bank_Account_Toy.ui.model.request.AccountDepositRequestModel;
import com.example.Bank_Account_Toy.ui.model.request.AccountDetailsRequestModel;
import com.example.Bank_Account_Toy.ui.model.request.AccountTransferRequestModel;
import com.example.Bank_Account_Toy.ui.model.request.SetAccountSettingsRequestModel;
import com.example.Bank_Account_Toy.ui.model.response.AccountRespByType;
import com.example.Bank_Account_Toy.ui.model.response.AccountRespSettingUpdate;
import com.example.Bank_Account_Toy.ui.model.response.AccountRest;
import com.example.Bank_Account_Toy.ui.model.response.AccountRestBalance;
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

    @PutMapping(path = "/balance")
    public AccountRestBalance accountDeposit(@RequestBody AccountDepositRequestModel balanceDetails) {
        AccountDto accountDto = new AccountDto();
        BeanUtils.copyProperties(balanceDetails, accountDto);

        AccountDto returnedUpdatedIban = accountService.accountDeposit(accountDto);
        AccountRestBalance returnValue = new AccountRestBalance();
        BeanUtils.copyProperties(returnedUpdatedIban, returnValue);
        return returnValue;
    }

    @GetMapping(path = "/balance/{iban}")
    public AccountRestBalance getAccountsBalance(@PathVariable String iban) {
        AccountRestBalance returnValue = new AccountRestBalance();

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

    @PostMapping(path = "/transfer")
    public String accountTransfer(@RequestBody AccountTransferRequestModel transferDetails) {

        //AccountTransferRequestModel returnValue = new AccountTransferRequestModel();
        AccountDto accountDto = new AccountDto();
        BeanUtils.copyProperties(transferDetails, accountDto);
        String transferSuccess = accountService.accountTransfer(accountDto);
        //BeanUtils.copyProperties(createdAccount, returnValue);

        return transferSuccess;
    }
}

