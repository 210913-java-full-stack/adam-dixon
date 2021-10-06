package utils.Menus;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MainMenu {
    Scanner sc = new Scanner(System.in);

    public MainMenu() {
    }

    /**
     * Prints a welcome screen which the shows the user the menus to which they can navigate
     */
    public void welcome() throws SQLException, IOException {
        System.out.println("\nWelcome Customer!\n\n1) Log in\n2) Register\n3) Quit");
        String choice = sc.nextLine();
        while (!choice.matches("[1-3]")){
            System.out.println("Invalid entry");
            choice = sc.nextLine();
        }
        switch (choice){
            case "1":
                LoginMenu lm = new LoginMenu();
                lm.loginAttempt();
                break;
            case "2":
                RegistrationMenu rm = new RegistrationMenu();
                rm.registerUser();
                break;
            case "3":
                return;
        }
    }
}


