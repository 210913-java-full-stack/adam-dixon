package utils.Menus;

import DAOs.AccountDAOs;
import DAOs.UserDAOs;
import Tables.User;
import utils.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginMenu {
    Connection conn = ConnectionManager.getConnection();
    Scanner sc = new Scanner(System.in);
    public static User user = new User();
    UserDAOs ud = new UserDAOs(conn);


    public LoginMenu() throws SQLException, IOException {
    }

    /**
     * Presents the user with a screen in which they can input their username and password
     * The user must input a username and password combination which matches an entry within the database
     * The user is given 5 login attempts before they must create new password
     * After the user logs in, they are sent to their account page
     */

    public void loginAttempt() throws SQLException, IOException {
        int attempts = 0;
        int attemptsLeft = 5;
        User test;
        System.out.println("==========Login Menu=========");
        System.out.println("\nUsername: ");
        String username = sc.nextLine();
        while (!username.matches("[a-zA-Z0-9]{6,20}")){
            System.out.println("Invalid username");
            username = sc.nextLine();
        }

        System.out.println("Password: ");
        String password = sc.nextLine();
        while (!password.matches("[a-zA-Z0-9]{6,20}")){
            System.out.println("Invalid password");
            password = sc.nextLine();
        }

        test = ud.getItemByID(username, password);

        attemptsLeft = attemptsLeft - 1;

        if (test == null){
            for (attempts = 1; attempts <= 5; attempts++){
                if (attempts == 5){
                    System.out.println("\nYou must create a new password");
                    RecoveryMenu rm = new RecoveryMenu();
                    rm.recoverAccount();
                }
                System.out.printf("Incorrect Username or Password. You have %d more attempts\n", (attemptsLeft));
                System.out.println("Username: ");
                username = sc.nextLine();
                while (!username.matches("[a-zA-Z0-9]{6,20}")){
                    System.out.println("Invalid username");
                    username = sc.nextLine();
                }
                System.out.println("Password: ");
                password = sc.nextLine();
                while (!password.matches("[a-zA-Z0-9]{6,20}")){
                    System.out.println("Invalid password");
                    password = sc.nextLine();
                }
                test = ud.getItemByID(username, password);
                --attemptsLeft;
                if (test != null) {
                    break;
                }
            }
        }
        AccountMenu am = new AccountMenu();
        am.accountPage(test);
    }
}
