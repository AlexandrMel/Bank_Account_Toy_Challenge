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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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

    // Service Layer Method to create a new Account
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
        //Inform user if the account failed to be created
        if (isNull(storedAccountDetails)) {
            throw new Exception(ErrorMessages.COULD_NOT_CREATE_NEW_ACCOUNT.getErrorMessage());
        }
        //Instantiate DTO and pass the DB data
        AccountDto returnValue = new AccountDto();
        BeanUtils.copyProperties(storedAccountDetails, returnValue);
        return returnValue;
    }

    // Service Layer Method to a list of accounts with details by account type
    public List<AccountDto> getAccountsByType(String accountType) {
        //Instantiate a List of Account Dto for return value
        List<AccountDto> returnValue = new ArrayList<AccountDto>();
        //Search for the accounts by type in the database using AccountRepository class
        List<AccountEntity> accountList = accountRepository.findAccountEntityByAccountType(accountType);
        //Looping the DB data and converting it to DTO objects
        for (AccountEntity accountEntity : accountList) {
            AccountDto accountDto = new AccountDto();
            BeanUtils.copyProperties(accountEntity, accountDto);
            returnValue.add(accountDto);
        }

        return returnValue;
    }

    // Service Layer Method to set a setting, specifically lock or unlock account
    public AccountDto setAccountSetting(AccountDto setting) throws Exception {
        //Instantiate an Account Dto for return value
        AccountDto returnValue = new AccountDto();
        //Search for the account by iban in the database using AccountRepository class
        AccountEntity foundAccount = accountRepository.findAccountByIban(setting.getIban());
        //switch to specify the setting to be changed, can be added more settings
        switch (setting.getSettingId()) {
            case "lock":
                foundAccount.setLocked(setting.getSettingValue());

        }
        //Save the foundAccount with the modified setting back in DB
        AccountEntity updatedIban = accountRepository.save(foundAccount);
        //Inform user id unable to update setting
        if (isNull(updatedIban)) {
            throw new Exception(ErrorMessages.COULD_NOT_CHANGE_SETTING.getErrorMessage());
        }
        BeanUtils.copyProperties(updatedIban, returnValue);
        return returnValue;
    }

    // Service Layer Method to get current specific account balance
    public AccountDto getAccountsBalance(String iban) {
        //Instantiate an Account Dto for return value
        AccountDto returnValue = new AccountDto();
        //Search for the account by iban in the database using AccountRepository class
        AccountEntity returnedAccount = accountRepository.findAccountByIban(iban);
        BeanUtils.copyProperties(returnedAccount, returnValue);
        return returnValue;
    }

    // Service Layer Method to make a transfer from an account to another account with restrictions
    public String accountTransfer(AccountDto transferDetails) throws Exception {
        //Instantiate return Value DTO
        AccountDto returnValue = new AccountDto();
        //Search in DB for the account that will be debited
        AccountEntity transferFromAccount = accountRepository.findAccountByIban(transferDetails.getIban());
        //Check if the account is locked
        if (transferFromAccount.getLocked()) {
            throw new Exception(ErrorMessages.ACCOUNT_LOCKED.getErrorMessage());
        }
        //Search in DB for the account that will be credited
        AccountEntity transferToAccount = accountRepository.findAccountByIban(transferDetails.getTransferToIban());
        //Check for the transferTo rules applied to the debited account
        String[] arrayOfRules = transferFromAccount.getSendMoneyTo().split(", ");
        //Check if the Debited account has sufficient funds
        if (Arrays.asList(arrayOfRules).contains(transferToAccount.getAccountType())) {
            if (transferFromAccount.getBalance().compareTo(transferDetails.getTransactionAmount()) < 0) {
                throw new Exception(ErrorMessages.INSUFFICIENT_FUNDS.getErrorMessage());
            }
            //Subtract the transfer amount from the debited account
            BigDecimal currentBalanceFromAccount = transferFromAccount.getBalance();
            BigDecimal newBalanceFromAccount = currentBalanceFromAccount.subtract(transferDetails.getTransactionAmount().abs());
            transferFromAccount.setBalance(newBalanceFromAccount);
            //Add the transfer amount to the credited account
            BigDecimal currentBalanceToAccount = transferToAccount.getBalance();
            BigDecimal newBalanceToAccount = currentBalanceToAccount.add(transferDetails.getTransactionAmount().abs());
            transferToAccount.setBalance(newBalanceToAccount);
            //Save updated debit account back to DB
            AccountEntity updatedFromAccount = accountRepository.save(transferFromAccount);
            //if save to DB successful save new transaction history for this account in transaction history table
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
            //Save updated credit account back to DB
            AccountEntity updatedToAccount = accountRepository.save(transferToAccount);
            //if save to DB successful save new transaction history for this account in transaction history table
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
            //Inform user if his account does not have the right to perform transfer to this specific acount
            throw new Exception(ErrorMessages.UNABLE_TO_TRANSFER_TO_ACCOUNT.getErrorMessage());
        }

    }

    // Service Layer Method to get a list of transactions for a specific account
    public List<AccountDto> getTransactionHistoryByIban(String iban) {
        //Instantiate a List of Account Dto for return value
        List<AccountDto> returnValue = new ArrayList<AccountDto>();
        //Search for the account history by iban in the database using TransactionHistoryRepository class
        List<TransactionHistoryEntity> accountList = transactionHistoryRepository.findTransactionHistoryEntitiesByIban(iban);
        //Looping through the data got from DB and converting them to DTO
        for (TransactionHistoryEntity transactionHistoryRepository : accountList) {
            AccountDto accountDto = new AccountDto();
            BeanUtils.copyProperties(transactionHistoryRepository, accountDto);
            returnValue.add(accountDto);
        }

        return returnValue;
    }
}
