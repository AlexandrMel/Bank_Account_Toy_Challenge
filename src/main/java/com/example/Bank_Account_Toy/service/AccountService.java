package com.example.Bank_Account_Toy.service;

import com.example.Bank_Account_Toy.shared.dto.AccountDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AccountService {
    List<AccountDto> getAccountsByType(String accountType);

    AccountDto createAccount(AccountDto account);

    AccountDto setAccountSetting(String iban, AccountDto setting);

    AccountDto getAccountsBalance(String iban);

    AccountDto accountDeposit(AccountDto balanceDetails);

    String accountTransfer(AccountDto transferDetails);


}
