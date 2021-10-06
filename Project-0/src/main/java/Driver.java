import DAOs.UserDAOs;
import ListStructure.MyList;
import utils.ConnectionManager;
import utils.Menus.MainMenu;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args)  {

        /*
        Opens the Main Menu for the user to interact with
         */
            try {
                MainMenu mm = new MainMenu();
                mm.welcome();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }




    }

