package com.example.Bank_Account_Toy.service;

import com.example.Bank_Account_Toy.shared.dto.AccountDto;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AccountService {
    List<AccountDto> getAccountsByType(String accountType);

    AccountDto createAccount(AccountDto account) throws Exception;

    AccountDto setAccountSetting(AccountDto setting);

    AccountDto getAccountsBalance(String iban);

    AccountDto accountDeposit(AccountDto balanceDetails) throws Exception;

    String accountTransfer(AccountDto transferDetails) throws Exception;

    List<AccountDto> getTransactionHistoryByIban(String iban);


}
