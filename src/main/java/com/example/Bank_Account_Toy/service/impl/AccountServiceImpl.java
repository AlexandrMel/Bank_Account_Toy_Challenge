package com.example.Bank_Account_Toy.service.impl;

import com.example.Bank_Account_Toy.io.entity.AccountEntity;
import com.example.Bank_Account_Toy.repository.AccountRepository;
import com.example.Bank_Account_Toy.service.AccountService;
import com.example.Bank_Account_Toy.shared.Utils;
import com.example.Bank_Account_Toy.shared.dto.AccountDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.isNull;


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

    public AccountDto accountDeposit(AccountDto balanceDetails) {

        AccountDto returnValue = new AccountDto();
        AccountEntity foundAccount = accountRepository.findAccountByIban(balanceDetails.getIban());
        BigDecimal newBalance = foundAccount.getBalance().add(balanceDetails.getBalance());
        foundAccount.setBalance(newBalance);
        AccountEntity updatedIban = accountRepository.save(foundAccount);
        BeanUtils.copyProperties(updatedIban, returnValue);
        return returnValue;
    }

    public AccountDto getAccountsBalance(String iban) {
        AccountDto returnValue = new AccountDto();

        AccountEntity returnedAccount = accountRepository.findAccountByIban(iban);
        BeanUtils.copyProperties(returnedAccount, returnValue);
        return returnValue;
    }

    public String accountTransfer(AccountDto transferDetails) {
        AccountDto returnValue = new AccountDto();
        AccountEntity transferFromAccount = accountRepository.findAccountByIban(transferDetails.getTransferFromIban());
        AccountEntity transferToAccount = accountRepository.findAccountByIban(transferDetails.getTransferToIban());
        BigDecimal currentBalanceFromAccount = transferFromAccount.getBalance();
        BigDecimal currentBalanceToAccount = transferToAccount.getBalance();
        BigDecimal newBalanceFromAccount = currentBalanceFromAccount.subtract(transferDetails.getTransferAmount());
        BigDecimal newBalanceToAccount = currentBalanceToAccount.add(transferDetails.getTransferAmount());
        transferFromAccount.setBalance(newBalanceFromAccount);
        transferToAccount.setBalance(newBalanceToAccount);
        AccountEntity updatedFromAccount = accountRepository.save(transferFromAccount);
        AccountEntity updatedToAccount = accountRepository.save(transferToAccount);
        if (!isNull(updatedFromAccount) && !isNull(updatedToAccount)) {

        }
        return "Transfer was successful!";
    }
}
