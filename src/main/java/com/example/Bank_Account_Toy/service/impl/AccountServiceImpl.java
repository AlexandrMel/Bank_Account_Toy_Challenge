package com.example.Bank_Account_Toy.service.impl;

import com.example.Bank_Account_Toy.io.entity.AccountEntity;
import com.example.Bank_Account_Toy.io.entity.TransactionHistoryEntity;
import com.example.Bank_Account_Toy.repository.AccountRepository;
import com.example.Bank_Account_Toy.repository.TransactionHistoryRepository;
import com.example.Bank_Account_Toy.service.AccountService;
import com.example.Bank_Account_Toy.shared.Utils;
import com.example.Bank_Account_Toy.shared.dto.AccountDto;
import com.example.Bank_Account_Toy.ui.model.response.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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


    // Service Layer Method to make a deposit to a specific Account
    @Override
    public AccountDto accountDeposit(AccountDto balanceDetails) throws Exception {
        //Instantiate an Account Dto for return value
        AccountDto returnValue = new AccountDto();
        //Search for the account by iban in the database using AccountRepository class
        AccountEntity foundAccount = accountRepository.findAccountByIban(balanceDetails.getIban());
        // Inform user is not Account with provided IBAN was found
        if (isNull(foundAccount)) {
            throw new Exception(ErrorMessages.NO_ACCOUNT_FOUND.getErrorMessage());
        }
        if (foundAccount.getLocked()) {
            throw new Exception(ErrorMessages.ACCOUNT_LOCKED.getErrorMessage());
        }
        //Update Account Balance with the specified amount, added - abs() so no negative value is possible,
        // but can be removed for withdrawal implementation
        BigDecimal newBalance = foundAccount.getBalance().add(balanceDetails.getTransactionAmount().abs());
        foundAccount.setBalance(newBalance);
        //Save Account with updated balance to DB;
        AccountEntity updatedIban = accountRepository.save(foundAccount);
        //if balance update is successful save transaction data in transaction history Table
        if (!isNull(updatedIban)) {
            //Instantiate
            TransactionHistoryEntity newTransaction = new TransactionHistoryEntity();
            //Copy Data to TransactionHistoryEntity
            BeanUtils.copyProperties(balanceDetails, newTransaction);
            //Add Extra fields
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            newTransaction.setCreatedAt(timestamp);
            newTransaction.setTransactionId(utils.generateIBAN(15, "mix"));
            newTransaction.setIbanPrefix_Sender_Receiver(updatedIban.getIbanPrefix());
            newTransaction.setIbanSender_Receiver(updatedIban.getIban());
            newTransaction.setBalanceAfterTransaction(updatedIban.getBalance());
            //Save new transaction in DB
            TransactionHistoryEntity savedTransaction = transactionHistoryRepository.save(newTransaction);
            //Inform user if transaction history not updated
            if (isNull(savedTransaction)) {
                throw new Exception(ErrorMessages.COULD_NOT_UPDATE_TRANSACTION_HISTORY.getErrorMessage());
            }
        } else {
            throw new Exception(ErrorMessages.COULD_NOT_UPDATE_BALANCE.getErrorMessage());
        }
        BeanUtils.copyProperties(updatedIban, returnValue);
        return returnValue;
    }


    @Override
    public AccountDto createAccount(AccountDto account) throws Exception {
        //Instantiate AccountEntity
        AccountEntity accountEntity = new AccountEntity();
        //Generate an IBAN using Utils method
        String generatedIBAN = utils.generateIBAN(35, "num");
        //Generate transfer Rules for account Type
        String generatedSendMoneyTo = utils.setAccountTransactionRules(account.getAccountType());
        //Set Account Iban Prefix
        account.setIbanPrefix("DE");
        //Not a perfect solution to check for duplicate in generated IBAN, should change for a while loop
        String generatedIban = !isNull(accountRepository.findAccountEntityByAccountType(generatedIBAN)) ? utils.generateIBAN(35, "num") : generatedIBAN;
        account.setIban(generatedIban);
        account.setSendMoneyTo(generatedSendMoneyTo);
        BeanUtils.copyProperties(account, accountEntity);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        accountEntity.setCreatedAt(timestamp);
        //Save new Account in DB
        AccountEntity storedAccountDetails = accountRepository.save(accountEntity);
        if (isNull(storedAccountDetails)) {
            throw new Exception(ErrorMessages.COULD_NOT_CREATE_NEW_ACCOUNT.getErrorMessage());
        }
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

    public AccountDto setAccountSetting(AccountDto setting) {

        AccountDto returnValue = new AccountDto();
        AccountEntity foundAccount = accountRepository.findAccountByIban(setting.getIban());
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

    public String accountTransfer(AccountDto transferDetails) throws Exception {

        AccountDto returnValue = new AccountDto();
        AccountEntity transferFromAccount = accountRepository.findAccountByIban(transferDetails.getIban());
        if (transferFromAccount.getLocked()) {
            throw new Exception(ErrorMessages.ACCOUNT_LOCKED.getErrorMessage());
        }
        AccountEntity transferToAccount = accountRepository.findAccountByIban(transferDetails.getTransferToIban());
        String[] arrayOfRules = transferFromAccount.getSendMoneyTo().split(", ");
        if (Arrays.asList(arrayOfRules).contains(transferToAccount.getAccountType())) {
            if (transferFromAccount.getBalance().compareTo(transferDetails.getTransactionAmount()) < 0) {
                throw new Exception(ErrorMessages.INSUFFICIENT_FUNDS.getErrorMessage());
            }

            BigDecimal currentBalanceFromAccount = transferFromAccount.getBalance();
            BigDecimal newBalanceFromAccount = currentBalanceFromAccount.subtract(transferDetails.getTransactionAmount());
            transferFromAccount.setBalance(newBalanceFromAccount);

            BigDecimal currentBalanceToAccount = transferToAccount.getBalance();
            BigDecimal newBalanceToAccount = currentBalanceToAccount.add(transferDetails.getTransactionAmount());
            transferToAccount.setBalance(newBalanceToAccount);
            AccountEntity updatedFromAccount = accountRepository.save(transferFromAccount);
            if (!isNull(updatedFromAccount)) {
                TransactionHistoryEntity newTransaction = new TransactionHistoryEntity();
                BeanUtils.copyProperties(transferDetails, newTransaction);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                newTransaction.setCreatedAt(timestamp);
                newTransaction.setTransactionId(utils.generateIBAN(15, "mix"));
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
                newTransaction.setTransactionId(utils.generateIBAN(15, "mix"));
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
        } else {
            throw new Exception(ErrorMessages.UNABLE_TO_TRANSFER_TO_ACCOUNT.getErrorMessage());
        }
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
