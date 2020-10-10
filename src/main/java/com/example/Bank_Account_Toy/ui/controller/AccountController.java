package com.example.Bank_Account_Toy.ui.controller;


import com.example.Bank_Account_Toy.service.AccountService;
import com.example.Bank_Account_Toy.shared.dto.AccountDto;
import com.example.Bank_Account_Toy.ui.model.request.AccountDetailsRequestModel;
import com.example.Bank_Account_Toy.ui.model.request.SetAccountSettingsRequestModel;
import com.example.Bank_Account_Toy.ui.model.response.AccountRespByType;
import com.example.Bank_Account_Toy.ui.model.response.AccountRespSettingUpdate;
import com.example.Bank_Account_Toy.ui.model.response.AccountRest;
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

    @PutMapping(path = "/settings/{iban}")
    public AccountRespSettingUpdate setAccountSettings(@PathVariable String iban, @RequestBody SetAccountSettingsRequestModel settingDetails) {
        AccountDto accountDto = new AccountDto();
        BeanUtils.copyProperties(settingDetails, accountDto);

        AccountDto returnedUpdatedIban = accountService.setAccountSetting(iban, accountDto);
        AccountRespSettingUpdate returnValue = new AccountRespSettingUpdate();
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

}

