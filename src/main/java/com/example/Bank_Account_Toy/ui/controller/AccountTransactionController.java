package com.example.Bank_Account_Toy.ui.controller;


import com.example.Bank_Account_Toy.service.AccountService;
import com.example.Bank_Account_Toy.shared.dto.AccountDto;
import com.example.Bank_Account_Toy.ui.model.request.AccountDetailsRequestModel;
import com.example.Bank_Account_Toy.ui.model.response.AccountRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("accounts/{userId}/") //http://localhost:8080/accounts
public class AccountTransactionController {

    @Autowired
    AccountService accountService;

    @GetMapping(path = "/balance/{iban}")
    public String getAccountsBalance(@PathVariable String iban) {


        return "get account was called";
    }


}

