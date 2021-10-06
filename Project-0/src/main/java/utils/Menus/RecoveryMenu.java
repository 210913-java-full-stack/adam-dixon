package utils.Menus;

import DAOs.UserDAOs;
import Tables.User;
import utils.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class RecoveryMenu {
    Connection conn = ConnectionManager.getConnection();
    UserDAOs ud = new UserDAOs(conn);
    Scanner sc = new Scanner(System.in);
    boolean userEntry = false;

    public RecoveryMenu() throws SQLException, IOException {
    }

    /**
     * The recoverAccount method allows the user to gain access and change the password to their user account
     * The User is prompted to enter their account username
     * Once their account information has been recovered, the user is able to change their account password
     * The new account password is saved to the user entry within the database table
     */
    public void recoverAccount() throws SQLException, IOException {
        User forgotten = new User();
        String username;
        String newPassword;
        System.out.println("Enter your username: ");
        userEntry = true;
        username = sc.nextLine();
        while (!username.matches("[a-zA-Z0-9]{6,20}")){
            System.out.println("Invalid username");
            username = sc.nextLine();
        }
        while (userEntry) {
            forgotten = ud.getItemByID(username);
            if (ud.getItemByID(username) == null) {
                System.out.println("This account does not exist.");
                System.out.println("Enter your username: ");
                username = sc.nextLine();
                while (!username.matches("[a-zA-Z0-9]{6,20}")){
                    System.out.println("Invalid username");
                    username = sc.nextLine();
                }
            } else {
                userEntry = false;
                System.out.println("Your account has been recovered.");
                System.out.println("Enter your new password: ");
                newPassword = sc.nextLine();
                while (!newPassword.matches("[a-zA-Z0-9]{6,20}")){
                    System.out.println("Invalid password");
                    newPassword = sc.nextLine();
                }
                forgotten.setPassword(newPassword);
            }
        }
        ud.save(forgotten);
        LoginMenu lm = new LoginMenu();
        lm.loginAttempt();
    }

}

