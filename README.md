# Bank_Account_Toy_Challenge
Bank_Account_Toy_Challenge
An REST API simulating some bank client features, like create account, 
get account balance, get account info by type, make transfer and deposits to your accounts

# Account types
• Checking account (functions as a reference account for the savings account)
• Savings account
• Private loan account
Every account has an IBAN assigned and should be referenced by this.

# Account rights
Checking account - transferring money from and to any account is possible
Savings account - transferring money from any account is possible. Only transferring
money from the savings account to the reference account (checking account) is possible.
Private loan account - transferring money from any account is possible. Withdrawal is not
possible.


To register new account make a POST request to "/accounts" while providing account details in the body
of the request. Example:
```
[POST] http://localhost:8080/accounts
{
    "firstName": "Alex",
    "lastName" : "Miller",
    "accountType": "checking",
    "userId": "kjncdlhwbelcwhebvc"
}
[RESPONSE EX.]
{
    "userId": "kjncdlhwbelcwhebvc",
    "accountType": "checking",
    "firstName": "Alex",
    "lastName": "Miller",
    "ibanPrefix": "DE",
    "iban": "79727933540822830562120678703027370"
}
```


### REST API endpoints ###
All of the available endpoints for the APi can be seen inside the "controller" package.

### Pre-requisite ###
To run the API, Java 11 or higher is mandatory, and Maven 3.6.0 or higher.

### Build the API and run it
Simply use Maven to create a runnable jar.-> "mvn package".
Run it using the terminal with the command "java -jar PACKAGE_NAME"

# ENDPOINTS

Here are examples how to interact with the API.

Change the base URL according to the actual URL and adjust the port if needed 
 * (application.properties -> server.port =YOUR_PORT)


### Accounts
Used for interacting with accounts


- Get all accounts with a specific type
```
[GET] http://localhost:8080/accounts/type/{accountType}

[RESPONSE EX.]
[
    {
        "userId": "kjncdlhwbelcwhebvc",
        "accountType": "checking",
        "ibanPrefix": "DE",
        "iban": "79727933540822830562120678703027370",
        "createdAt": "2020-10-12T22:33:11.000+00:00",
        "locked": false
    },
    {
        "userId": "kjncdlhwbelcwhebvc",
        "accountType": "checking",
        "ibanPrefix": "DE",
        "iban": "90442271095018230717996724479280806",
        "createdAt": "2020-10-12T22:34:02.000+00:00",
        "locked": false
    },
```
- Get the current balance of a specific account
```
[GET] http://localhost:8080/accounts/balance/{IBAN}

[RESPONSE EX.]
{
    "userId": "kjncdlhwbelcwhebvc",
    "accountType": "checking",
    "ibanPrefix": "DE",
    "iban": "90442271095018230717996724479280806",
    "balance": 0.00
}
```
- Make a deposit to a specific account
```
[PUT] http://localhost:8080/accounts/balance
{
    "transactionType": "Credit",
    "userId": "khbvkhgvcghj",
    "iban": "90442271095018230717996724479280806",
    "ibanPrefix": "DE",
    "transactionAmount": 25.55,
    "transactionPurpose": "Account Deposit"
}

[RESPONSE EX.]
{
    "userId": "kjncdlhwbelcwhebvc",
    "accountType": "checking",
    "ibanPrefix": "DE",
    "iban": "90442271095018230717996724479280806",
    "balance": 25.55
}
```
- Set account setting, in particular lock/unlock account 
```
[PUT] http://localhost:8080/accounts/settings
{
    "userId": "test",
    "iban": "22416209876452422937881675185699291",
    "settingId": "lock",
    "settingValue": false
}

[RESPONSE EX.]
{
    "ibanPrefix": "DE",
    "iban": "90442271095018230717996724479280806",
    "accountType": "checking",
    "userId": "kjncdlhwbelcwhebvc",
    "locked": false
}
```
- Make a transfer from an account to another account
```
[POST] http://localhost:8080/accounts/transaction
{
    "userId": "jncikwncihw",
    "ibanPrefix": "DE",
    "iban": "90442271095018230717996724479280806",
    "transferToIbanPrefix": "DE",
    "transferToIban": "04514461690313467950288243387099675",
    "transactionAmount": 10.50,
    "transactionPurpose": "Some money to you",
    "transactionType": "Credit"
}

[RESPONSE EX.]
Transfer was successful!
```
### Transaction History
Used for transaction history for each account


- Get transaction history for a specific IBAN
```
[GET] http://localhost:8080/accounts/transaction/{IBAN}

[RESPONSE EX.]
[
    {
        "transactionPurpose": "Account Deposit",
        "transactionAmount": 25.55,
        "transactionType": "Credit",
        "transactionId": "xWrx3Y4Xg574auI",
        "createdAt": "2020-10-12T22:39:35.000+00:00",
        "ibanSender_Receiver": "90442271095018230717996724479280806",
        "ibanPrefix_Sender_Receiver": "DE",
        "balanceAfterTransaction": 25.55
    },
    {
        "transactionPurpose": "Some money to you",
        "transactionAmount": -10.50,
        "transactionType": "Debit",
        "transactionId": "RNcMpsHSRw1cE3f",
        "createdAt": "2020-10-12T22:46:18.000+00:00",
        "ibanSender_Receiver": "04514461690313467950288243387099675",
        "ibanPrefix_Sender_Receiver": "DE",
        "balanceAfterTransaction": 15.05
    }
]
```
