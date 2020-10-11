package com.example.Bank_Account_Toy.service.impl;

import com.example.Bank_Account_Toy.io.entity.AccountEntity;
import com.example.Bank_Account_Toy.io.entity.TransactionHistoryEntity;
import com.example.Bank_Account_Toy.repository.AccountRepository;
import com.example.Bank_Account_Toy.repository.TransactionHistoryRepository;
import com.example.Bank_Account_Toy.service.AccountService;
import com.example.Bank_Account_Toy.shared.Utils;
import com.example.Bank_Account_Toy.shared.dto.AccountDto;
import javassist.NotFoundException;
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


    //Method to make a deposit to a specific Account
    @Override
    public AccountDto accountDeposit(AccountDto balanceDetails) throws NotFoundException {
//Instantiate an Account Dto for return value
        AccountDto returnValue = new AccountDto();
        //Search for the account by iban in the database using AccountRepository class
        AccountEntity foundAccount = accountRepository.findAccountByIban(balanceDetails.getIban());
        if (isNull(foundAccount)) {
            throw new NotFoundException("No Account found with this IBAN!, please provide a valid IBAN");
        }
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


    public AccountDto getAccountsBalance(String iban) {
        AccountDto returnValue = new AccountDto();

        AccountEntity returnedAccount = accountRepository.findAccountByIban(iban);
        BeanUtils.copyProperties(returnedAccount, returnValue);
        return returnValue;
    }

    public String accountTransfer(AccountDto transferDetails) {
        AccountDto returnValue = new AccountDto();
        AccountEntity transferFromAccount = accountRepository.findAccountByIban(transferDetails.getIban());
        AccountEntity transferToAccount = accountRepository.findAccountByIban(transferDetails.getTransferToIban());
        BigDecimal currentBalanceFromAccount = transferFromAccount.getBalance();
        BigDecimal currentBalanceToAccount = transferToAccount.getBalance();
        BigDecimal newBalanceFromAccount = currentBalanceFromAccount.subtract(transferDetails.getTransactionAmount());
        BigDecimal newBalanceToAccount = currentBalanceToAccount.add(transferDetails.getTransactionAmount());
        transferFromAccount.setBalance(newBalanceFromAccount);
        transferToAccount.setBalance(newBalanceToAccount);
        AccountEntity updatedFromAccount = accountRepository.save(transferFromAccount);
        if (!isNull(updatedFromAccount)) {
            TransactionHistoryEntity newTransaction = new TransactionHistoryEntity();
            BeanUtils.copyProperties(transferDetails, newTransaction);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            newTransaction.setCreatedAt(timestamp);
            newTransaction.setTransactionId(utils.generateIBAN(15, "userId"));
            newTransaction.setTransactionType("Debit");
            newTransaction.setTransactionAmount(transferDetails.getTransactionAmount().negate());
            newTransaction.setIbanPrefix_Sender_Receiver(transferDetails.getTransferToIbanPrefix());
            newTransaction.setIbanSender_Receiver(transferDetails.getTransferToIban());
            newTransaction.setBalanceAfterTransaction(updatedFromAccount.getBalance());
            TransactionHistoryEntity savedTransaction = transactionHistoryRepository.save(newTransaction);
        }
        AccountEntity updatedToAccount = accountRepository.save(transferToAccount);
        if (!isNull(updatedToAccount)) {
            TransactionHistoryEntity newTransaction = new TransactionHistoryEntity();
            BeanUtils.copyProperties(transferDetails, newTransaction);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            newTransaction.setCreatedAt(timestamp);
            newTransaction.setTransactionId(utils.generateIBAN(15, "userId"));
            newTransaction.setTransactionType("Credit");
            newTransaction.setIban(transferDetails.getTransferToIban());
            newTransaction.setIbanPrefix(transferDetails.getIbanPrefix());
            newTransaction.setTransactionAmount(transferDetails.getTransactionAmount());
            newTransaction.setIbanPrefix_Sender_Receiver(transferDetails.getIbanPrefix());
            newTransaction.setIbanSender_Receiver(transferDetails.getIban());
            newTransaction.setBalanceAfterTransaction(updatedToAccount.getBalance());
            TransactionHistoryEntity savedTransaction = transactionHistoryRepository.save(newTransaction);
        }
        return "Transfer was successful!";
    }

    public List<AccountDto> getTransactionHistoryByIban(String iban) {
        List<AccountDto> returnValue = new ArrayList<AccountDto>();
        List<TransactionHistoryEntity> accountList = transactionHistoryRepository.findTransactionHistoryEntitiesByIban(iban);
        for (TransactionHistoryEntity transactionHistoryRepository : accountList) {
            AccountDto accountDto = new AccountDto();
            BeanUtils.copyProperties(transactionHistoryRepository, accountDto);
            returnValue.add(accountDto);
        }

        return returnValue;
    }
}
