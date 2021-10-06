package utils.Menus;

import DAOs.AccountDAOs;
import DAOs.StatementDAOs;
import DAOs.UserDAOs;
import ExceptionHandling.InvalidNameException;
import ExceptionHandling.StringInputException;
import Tables.Account;
import Tables.BankStatement;
import Tables.User;
import utils.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RegistrationMenu {
    Connection conn = ConnectionManager.getConnection();
    Scanner sc = new Scanner(System.in);
    User user = new User();
    Account account = new Account();
    UserDAOs ud = new UserDAOs(conn);
    AccountDAOs ad = new AccountDAOs(conn);
    StatementDAOs sd = new StatementDAOs(conn);
    boolean userEntry = false;

    public RegistrationMenu() throws SQLException, IOException {
    }

    /**
     * This method registers and creates a new User Account
     * The User is prompted to provide necessary information
     *      First Name, Last Name, Username, Password
     * After providing this information, the User Account will be created
     * The User will then be navigated to the registerAccount method
     */
    public void registerUser() throws SQLException, IOException {
        System.out.print("Enter your First Name: ");
        String first = sc.nextLine();
        while (!first.matches("[a-zA-Z]{1,20}")) {
            System.out.println("Invalid entry");
            first = sc.nextLine();
        }
        user.setFirstName(first);

        System.out.print("Enter your Last Name: ");
        String last = sc.nextLine();
        while (!last.matches("[a-zA-Z]{1,20}")) {
            System.out.println("Invalid entry");
            last = sc.nextLine();
        }
        user.setLastName(last);

        System.out.print("Create a username: ");
        String username = sc.nextLine();
        while (!username.matches("[a-zA-Z0-9]{6,20}")) {
            System.out.println("Invalid entry");
            username = sc.nextLine();
        }
        user.setUsername(username);

        System.out.print("Create a password: ");
        String password = sc.nextLine();
        while (!password.matches("[a-zA-Z0-9]{6,20}")) {
            System.out.println("Invalid entry");
            password = sc.nextLine();
        }
        user.setPassword(password);

        try {
            ud.save(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("User Account Created.");
        registerAccount(user);
        }

    /**
     * The registerAccount method is used to create a new Bank Account for a user
     * The user is prompted to select what type of account they would like to open
     *      Checking or Savings
     * The User is then asked if they would like to make an initial deposit into their new bank account
     *      If no, the account is set to $0.00
     *      If yes, the User is prompted to enter the amount which they would like to deposit
     * A Bank Statement is created to hold the details of this transaction
     * The User is then navigated to the loginAttempt menu which will ask the user to log-in to their new account
     */
    public void registerAccount(User user) throws SQLException, IOException {
        Account temp = new Account();
        user = ud.getItemByID(user.getUsername());
        temp.setUser_id(user.getUser_id());
        System.out.println("What type of account would you like to open?\n1) Checking\n2) Savings");
        String choice = sc.nextLine();
        while (!choice.matches("[1-2]")){
            System.out.println("Invalid entry");
            choice = sc.nextLine();
        }
        switch (choice) {
            case "1":
                temp.setAccount_type("Checking");
                break;
            case "2":
                temp.setAccount_type("Savings");
                break;
        }
        temp.setBalance(0.00);
        ad.save(temp);
        AccountMenu am = new AccountMenu();
        am.accountPage(user);
    }
}
