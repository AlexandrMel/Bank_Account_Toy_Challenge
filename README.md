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


- Get specific users details
```
[GET] http://localhost:8081/users/{USER_ID}
```
- Create a new user
```
[POST] http://localhost:8081/users
{
	"firstName": FIRST_NAME,
	"lastName": LAST_NAME,
	"email": USER_EMAIL,
	"password": PASSWORD 
}
```
- Update user details
```
[PUT] http://localhost:8081/users/{USER_ID}
{
	"firstName": FIRST_NAME,
	"lastName": LAST_NAME,
	"email": USER_EMAIL,
	"password": PASSWORD 
}
```
- Delete a user
```
[DELETE] http://localhost:8081/users/{USER_ID}
```

