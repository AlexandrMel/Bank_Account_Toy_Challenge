package com.example.Bank_Account_Toy.service.impl;

import com.example.Bank_Account_Toy.io.entity.AccountEntity;
import com.example.Bank_Account_Toy.io.entity.TransactionHistoryEntity;
import com.example.Bank_Account_Toy.repository.AccountRepository;
import com.example.Bank_Account_Toy.repository.TransactionHistoryRepository;
import com.example.Bank_Account_Toy.service.AccountService;
import com.example.Bank_Account_Toy.shared.Utils;
import com.example.Bank_Account_Toy.shared.dto.AccountDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

import static java.util.Objects.isNull;


@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;
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
        BigDecimal newBalance = foundAccount.getBalance().add(balanceDetails.getTransactionAmount());
        foundAccount.setBalance(newBalance);
        AccountEntity updatedIban = accountRepository.save(foundAccount);
        if (!isNull(updatedIban)) {
            TransactionHistoryEntity newTransaction = new TransactionHistoryEntity();
            BeanUtils.copyProperties(balanceDetails, newTransaction);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            newTransaction.setCreatedAt(timestamp);
            newTransaction.setTransactionId(utils.generateIBAN(15, "userId"));
            newTransaction.setIbanPrefix_Sender_Receiver(updatedIban.getIbanPrefix());
            newTransaction.setIbanSender_Receiver(updatedIban.getIban());
            newTransaction.setBalanceAfterTransaction(updatedIban.getBalance());
            TransactionHistoryEntity savedTransaction = transactionHistoryRepository.save(newTransaction);
        }
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
        BigDecimal newBalanceFromAccount = currentBalanceFromAccount.subtract(transferDetails.getTransactionAmount());
        BigDecimal newBalanceToAccount = currentBalanceToAccount.add(transferDetails.getTransactionAmount());
        transferFromAccount.setBalance(newBalanceFromAccount);
        transferToAccount.setBalance(newBalanceToAccount);
        AccountEntity updatedFromAccount = accountRepository.save(transferFromAccount);
        AccountEntity updatedToAccount = accountRepository.save(transferToAccount);
        if (!isNull(updatedFromAccount) && !isNull(updatedToAccount)) {

        }
        return "Transfer was successful!";
    }
}
