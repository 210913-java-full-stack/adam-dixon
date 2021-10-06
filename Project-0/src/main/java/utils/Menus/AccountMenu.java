package utils.Menus;

import DAOs.AccountDAOs;
import DAOs.StatementDAOs;
import DAOs.UserDAOs;
import ListStructure.MyList;
import Tables.Account;
import Tables.BankStatement;
import Tables.User;
import utils.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Objects;
import java.util.Scanner;

public class AccountMenu extends LoginMenu {
    Connection conn = ConnectionManager.getConnection();
    Scanner sc = new Scanner(System.in);
    User user = new User();
    Account account = new Account();
    AccountDAOs ad = new AccountDAOs(conn);
    StatementDAOs state = new StatementDAOs(conn);
    boolean customerEntry = false;

    public AccountMenu() throws SQLException, IOException {
    }

    /**
     * Presents the logged-in user with the options presented to them on their account page
     * Asks the user for their input to select which option they would like to take part in
     * Based on the user input, the proper method will be called to execute the user's need
     */
    public void accountPage(User user) throws SQLException, IOException {
        System.out.println("\n" + user.getUsername() + "'s Account Page\n");
        System.out.println("What would you like to do? ");
        System.out.println("1) Account Balance\n2) Deposit Funds\n3) Withdraw Funds\n4) Transfer Money\n5) Account Activity\n6) Create a new Bank Account\n7) Sign Out");
        String choice = sc.nextLine();
        while (!choice.matches("[1-7]")) {
            System.out.println("Invalid entry");
            choice = sc.nextLine();
        }
        switch (choice) {
            case "1":
                accountBalance(user);
                break;
            case "2":
                deposit(user);
                break;
            case "3":
                withdraw(user);
                break;
            case "4":
                moneyTransfer(user);
                break;
            case "5":
                accountActivity(user);
                break;
            case "6":
                RegistrationMenu rm = new RegistrationMenu();
                rm.registerAccount(user);
            case "7":
                MainMenu mm = new MainMenu();
                mm.welcome();
        }

    }

    /**
     * Prints the user's account balance to the console screen
     * If the user has more than one bank account, all of their account balances will be printed to the screen
     * After the balances are printed to the screen, the user will be sent back to the account's main menu
     */
    public void accountBalance(User user) throws SQLException, IOException {
        String user_id = Integer.toString(user.getUser_id());
        MyList<Account> userAccounts;
        userAccounts = ad.getAllItems(user_id);
        System.out.println("Account ID       Type                    Balance");
        for (int i = 0; i < userAccounts.size(); i++) {
            Account entry = userAccounts.get(i);
            System.out.printf("%d        %-8s           $%11.2f\n",
                    entry.getAccount_id(),
                    entry.getAccount_type(),
                    entry.getBalance());
        }
        accountPage(user);
    }

    /**
     * Allows the user to transfer money from their account to another account
     * The user will be asked to identify the receiving account with the last four digits of the account ID
     * After a successful entry, the user will be prompted to enter an amount that they would like to transfer
     * If the four digits identify the user's bank account, they will be asked if they want to make a deposit
     * If the four digits do not correspond to a bank account within the database, the user will be asked to re-enter another set of four digits
     * The funds will be added to the balance of the bank account tied to the given account ID
     * The finds will be withdrawn from the balance of the user's bank account
     * A bank statement will be created which the user will be able to view in their "Account Activity" page
     * The User is then navigated back to the User Account Page's Main Menu
     */
    public void moneyTransfer(User user) throws SQLException, IOException {
        String user_id = Integer.toString(user.getUser_id());

        //Initialize a list of "account" objects
        MyList<Account> accounts;
        //Fills the list with all the bank accounts connected to the user id
        accounts = ad.getAllItems(user_id);
        String account_id = null;
        String fundsReceived = null;
        double transferFunds = 0;
        customerEntry = true;

        //If the user has more than one bank account, the user will be asked which account they would like to withdraw money from
        if (accounts.size() > 1) {
            System.out.println("What account would you like to withdraw money from?");
            System.out.println("Enter the last four digits of your Account ID: ");
            while (customerEntry){
                account_id = sc.nextLine();
                while (!account_id.matches("[0-9]{4}")) {
                    System.out.println("Invalid entry");
                    account_id = sc.nextLine();
                }
                account_id = "87932" + account_id;

                //Checks to see if the provided account ID exists
                for (int i = 0; i <= accounts.size(); i++) {
                    if (i == accounts.size()){
                        System.out.println("Incorrect Account ID\nEnter the last four digits of your Account ID");
                        break;
                    }

                    //Assigns a String object of the Account ID at the specific index to the reference variable, "temp"
                    String temp = Integer.toString(accounts.get(i).getAccount_id());
                    //If the temp equals the provided Account ID, the account object at the specific index, i, will be assigned to the "account" variable
                    if (account_id.equals(temp)) {
                        account = accounts.get(i);
                        customerEntry = false;
                        break;
                    }
                }
            }
        }
        //If the User only has one bank account, the single bank account will be assigned to the reference variable, "account"
        else {
            account = accounts.get(0);
        }
        System.out.println("What account would you like to transfer money to?");
        System.out.println("Enter the last four digits of your Account ID: ");
        customerEntry = true;
        //Grab the properties of the account for which the user provided the Account ID
        while (customerEntry) {
            account_id = sc.nextLine();
                while (!account_id.matches("[0-9]{4}")) {
                    System.out.println("Invalid entry");
                    account_id = sc.nextLine();
                }
                account_id = "87932" + account_id;
                //If the AccountDAO operation returns a null value
                if (ad.getItemByID(Integer.parseInt(account_id)) == null) {
                    System.out.println("This account does not Exist. Please enter a new Account ID\n" +
                            "Last four digits of Account ID: ");
                }
                //If the AccountDAO operation returns the same bank account
                else if (Objects.equals(account_id, Integer.toString(account.getAccount_id()))) {
                    System.out.println("You have entered your account ID. Would you like to make a deposit?\n" +
                            "1) Yes\n2) No");
                    int answer = sc.nextInt();
                    switch (answer) {
                        case 1:
                            deposit(user);
                            customerEntry = false;
                            break;
                        case 2:
                            break;
                    }
                } else {
                    customerEntry = false;
                }
        }
        //The Account returned by the AccountDAO operation is assigned to the "TransferTo" reference variable
            Account transferTo = ad.getItemByID(Integer.parseInt(account_id));


            //Ask the user the amount they wish to transfer
            System.out.print("Transfer Amount: ");
            //Grab the user input and store it in a variable named "fundsReceived"
            customerEntry = true;
            while (customerEntry) {
                fundsReceived = sc.nextLine();
                while (!fundsReceived.matches("[0-9]{1,10}")) {
                    System.out.println("Invalid entry");
                    fundsReceived = sc.nextLine();
                }
                transferFunds = Double.parseDouble(fundsReceived);
                //If the transfer amount is greater than the account balance
                if (transferFunds > account.getBalance()) {
                    System.out.println("Insufficient Funds. Enter a new amount.\n" +
                            "Transfer Amount: ");
                }
                //The transfer amount must be a positive amount
                else if (transferFunds <= 0) {
                    System.out.println("You must enter a positive amount. Enter a new amount.\n" +
                            "Transfer Amount: ");
                }
                //There is no issue with the transfer amount
                else {
                    customerEntry = false;
                }
            }
            //Grab the present balance of the account which will receive the transfer amount
            double preTransfer = transferTo.getBalance();
            double postTransfer = preTransfer + transferFunds;
            transferTo.setBalance(postTransfer);
            //Update the balance of the account which the money was transferred to
            ad.save(transferTo);

            //Create a Bank Statement entry which lays out the details of the transfer
            BankStatement transferState = new BankStatement();
            transferState.setUser_id(transferTo.getUser_id());
            transferState.setAccount_id(transferTo.getAccount_id());
            transferState.setAccount_type(transferTo.getAccount_type());
            transferState.setTransaction_type("Account Transfer");
            transferState.setAmount(transferFunds);
            transferState.setBalance(postTransfer);
            state.save(transferState);

            //Update the balance of the account from which the account was transferred
            double fundsTransferred = (transferFunds / -1);
            preTransfer = account.getBalance();
            postTransfer = preTransfer + fundsTransferred;
            account.setBalance(postTransfer);

            ////Create a Bank Statement entry which lays out the details of the transfer
            transferState.setUser_id(user.getUser_id());
            transferState.setAccount_id(account.getAccount_id());
            transferState.setAccount_type(account.getAccount_type());
            transferState.setTransaction_type("Account Transfer");
            transferState.setAmount(fundsTransferred);
            transferState.setBalance(postTransfer);
            state.save(transferState);
            ad.save(account);
            accountPage(user);
        }

        /**
         * Allows the user to deposit money into their account
         * Prompts the user to enter the amount that they would like to deposit into the account
         *      The user must enter a positive numerical value
         * The deposit amount is added to the user's balance
         * A bank statement is created which the user can view on their "Account Activity" page
         * The user is then navigated back to the User Account Page's Main Menu
         */
        public void deposit (User user) throws SQLException, IOException {
            double fundsDeposited = 0;
            String user_id = Integer.toString(user.getUser_id());
            MyList<Account> accounts;
            accounts = ad.getAllItems(user_id);
            customerEntry = true;
            if (accounts.size() > 1) {
                System.out.println("What account would you like to deposit money into?");
                System.out.println("Enter the last four digits of your Account ID: ");
            while (customerEntry){
                String account_id = sc.nextLine();
                    while (!account_id.matches("[0-9]{4}")) {
                        System.out.println("Invalid entry");
                        account_id = sc.nextLine();
                    }
                    account_id = "87932" + account_id;
                    //Iterates through the list of accounts
                    for (int i = 0; i <= accounts.size(); i++) {
                        //If the for loop iterates outside the list indices
                        if (i == accounts.size()){
                            System.out.println("Incorrect Account ID\nEnter the last four digits of your Account ID");
                            break;
                        }
                        //Assigns a String object of the Account ID at the specific index to the reference variable, "temp"
                        String temp = Integer.toString(accounts.get(i).getAccount_id());
                        //If the temp equals the provided Account ID, the account object at the specific index, i, will be assigned to the "account" variable
                        if (account_id.equals(temp)) {
                            account = accounts.get(i);
                            customerEntry = false;
                            break;
                        }
                    }
                }
            }
            //If the User only has one bank account, the single bank account will be assigned to the reference variable, "account"
            else {
                account = accounts.get(0);
        }

            //Ask the user for how much money they would like to deposit
            System.out.print("Deposit Amount: ");
            //Grab the input from the user and set the value equal to the variable "deposit"
            customerEntry = true;
            while (customerEntry) {
                String deposit = sc.nextLine();
                while (!deposit.matches("[0-9]{1,10}")) {
                    System.out.println("Invalid entry");
                    deposit = sc.nextLine();
                }
                fundsDeposited = Double.parseDouble(deposit);
                if (fundsDeposited <= 0) {
                    System.out.println("You must enter a positive amount. Enter a new amount.\n" +
                            "Deposit Amount: ");
                } else {
                    customerEntry = false;
                }
            }
            //Grab the present balance from the user's account
            double preDeposit = account.getBalance();
            //Initialize a new variable which will store the sum of the deposit amount and the user's present balance
            double postDeposit = preDeposit + fundsDeposited;
            //Change the user's account balance according to their given deposit amount
            account.setBalance(postDeposit);

            //Create a Bank Statement entry which will display details about the transaction
            BankStatement depositState = new BankStatement();
            depositState.setUser_id(user.getUser_id());
            depositState.setAccount_id(account.getAccount_id());
            depositState.setAccount_type(account.getAccount_type());
            depositState.setTransaction_type("Deposit");
            depositState.setAmount(fundsDeposited);
            depositState.setBalance(postDeposit);
            state.save(depositState);
            ad.save(account);
            accountPage(user);
        }

        /**
         * Withdraws money from the user's account
         * Prompts the user to enter an amount that they would like to withdraw from their account
         * This amount is deducted from the user account
         * A bank statement is created which the user will be able to view in their "Account Activity" page
         * The user is then navigated back to the Account Page's Main Menu
         */
        public void withdraw (User user) throws SQLException, IOException {
            double fundsWithdrawn = 0;
            String user_id = Integer.toString(user.getUser_id());
            MyList<Account> accounts;
            accounts = ad.getAllItems(user_id);
            customerEntry = true;
            if (accounts.size() > 1) {
                System.out.println("What account would you like to withdraw money from?");
                System.out.println("Enter the last four digits of your Account ID: ");
                while (customerEntry){
                    String account_id = sc.nextLine();
                    while (!account_id.matches("[0-9]{4}")) {
                        System.out.println("Invalid entry");
                        account_id = sc.nextLine();
                    }
                    account_id = "87932" + account_id;
                    for (int i = 0; i <= accounts.size(); i++) {
                        if (i == accounts.size()){
                            System.out.println("Incorrect Account ID\nEnter the last four digits of your Account ID");
                            break;
                        }
                        String temp = Integer.toString(accounts.get(i).getAccount_id());
                        if (account_id.equals(temp)) {
                            account = accounts.get(i);
                            customerEntry = false;
                            break;
                        }
                    }
                }
            } else {
                account = accounts.get(0);
            }

            //Ask the user for how much money they would like to withdraw
            System.out.print("Withdrawal Amount: ");
            //Grab the input from the user and set the value equal to the variable "withdrawal"
            customerEntry = true;
            while (customerEntry) {
                String withdrawal = sc.nextLine();
                while (!withdrawal.matches("[0-9]{1,10}")) {
                    System.out.println("Invalid entry");
                    withdrawal = sc.nextLine();
                }
                fundsWithdrawn = Double.parseDouble(withdrawal);
                if (fundsWithdrawn > account.getBalance()) {
                    System.out.println("Insufficient Funds. Enter a new amount.\n" +
                            "Withdrawal Amount: ");
                } else if (fundsWithdrawn <= 0) {
                    System.out.println("You must enter a positive amount. Enter a new amount.\n" +
                            "Withdrawal Amount: ");
                } else {
                    customerEntry = false;
                }
            }
            fundsWithdrawn = (fundsWithdrawn / -1);
            //Grab the present balance from the user's account
            double preWithdrawal = account.getBalance();
            //Initialize a new variable which will store the sum of the withdrawal amount and the user's present balance
            double postWithdrawal = preWithdrawal + fundsWithdrawn;
            //Change the user's account balance according to their given withdrawal amount
            account.setBalance(postWithdrawal);

            //Create a Bank Statement entry which will display details about the transaction
            BankStatement withdrawState = new BankStatement();
            withdrawState.setUser_id(user.getUser_id());
            withdrawState.setAccount_id(account.getAccount_id());
            withdrawState.setAccount_type(account.getAccount_type());
            withdrawState.setTransaction_type("Withdrawal");
            withdrawState.setAmount(fundsWithdrawn);
            withdrawState.setBalance(postWithdrawal);
            state.save(withdrawState);
            ad.save(account);
            accountPage(user);
        }

        /**
         * Prints a list of all the user's transactions(deposits, withdrawals, account transfers)
         * Provides the user with all the details of their previous transactions
         *      Transaction ID, Account ID, Account Type, Transaction Type, Amount, Account Balance
         * The user is navigated back to the Account Page's Main Menu
         */
        public void accountActivity (User user) throws SQLException, IOException {
            String user_id = Integer.toString(user.getUser_id());
            MyList<BankStatement> userStatements;
            userStatements = state.getAllItems(user_id);
            System.out.println("Transaction ID      Account ID      Type           Transaction                   Amount            Balance");
            for (int i = 0; i < userStatements.size(); i++) {
                BankStatement entry = userStatements.get(i);
                System.out.printf("     %3d              %d       %-8s       %-16s        $%11.2f       $%11.2f\n",
                        entry.getTransaction_id(),
                        entry.getAccount_id(),
                        entry.getAccount_type(),
                        entry.getTransaction_type(),
                        entry.getAmount(),
                        entry.getBalance());
            }
            accountPage(user);
        }

}

