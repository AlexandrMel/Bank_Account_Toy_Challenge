package com.example.Bank_Account_Toy.service.impl;

import com.example.Bank_Account_Toy.io.entity.AccountEntity;
import com.example.Bank_Account_Toy.repository.AccountRepository;
import com.example.Bank_Account_Toy.service.AccountService;
import com.example.Bank_Account_Toy.shared.Utils;
import com.example.Bank_Account_Toy.shared.dto.AccountDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    Utils utils;

    @Override
    public AccountDto createAccount(AccountDto account) {
        AccountEntity accountEntity = new AccountEntity();
        String generatedIBAN = utils.generateIBAN(35, "num");
        String generatedReceiveMoneyFrom = Arrays.toString(utils.setAccountTransactionRules(account.getAccountType())[0]);
        String generatedSendMoneyTo = Arrays.toString(utils.setAccountTransactionRules(account.getAccountType())[1]);
        account.setIbanPrefix("DE");
        account.setIban(generatedIBAN);
        account.setReceiveMoneyFrom(generatedReceiveMoneyFrom);
        account.setSendMoneyTo(generatedSendMoneyTo);
        BeanUtils.copyProperties(account, accountEntity);
        AccountEntity storedAccountDetails = accountRepository.save(accountEntity);
        AccountDto returnValue = new AccountDto();
        BeanUtils.copyProperties(storedAccountDetails, returnValue);
        return returnValue;
    }

    public List<AccountDto> getAccountsByType(String accountType) {
        List<AccountDto> returnValue = new ArrayList<AccountDto>();
        List<AccountEntity> accountList = accountRepository.findAccountEntityByAccountType(accountType);
        for (AccountEntity accountEntity : accountList) {
            AccountDto accountDto = new AccountDto();
            BeanUtils.copyProperties(accountEntity, accountDto);
            returnValue.add(accountDto);
        }

        return returnValue;
    }

    public AccountDto setAccountSetting(String iban, AccountDto setting) {

        AccountDto returnValue = new AccountDto();
        AccountEntity foundAccount = accountRepository.findAccountByIban(iban);
        switch (setting.getSettingId()) {
            case "lock":
                foundAccount.setLocked(setting.getSettingValue());

        }
        AccountEntity updatedIban = accountRepository.save(foundAccount);
        BeanUtils.copyProperties(updatedIban, returnValue);
        return returnValue;


    }
}
