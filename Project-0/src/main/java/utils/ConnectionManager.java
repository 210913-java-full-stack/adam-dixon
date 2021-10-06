package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


//This class manages the connections that we will make between this Java application and our SQL database
public class ConnectionManager {
    private static Connection conn;

    public ConnectionManager(Connection conn) {
        this.conn = conn;
    }

    //Method for creating connection between Java and SQL database
    public static Connection getConnection() throws IOException, SQLException {
        if (conn == null){
            Properties props = new Properties ();
            FileReader connectionProperties = new FileReader("src/main/resources/connection.properties");
            props.load(connectionProperties);

            //"jdbc:mariadb://hostname:port/databaseName?user=username&password=password"
            String connString = "jdbc:mariadb://" +
                    props.getProperty("hostname") + ":" +
                    props.getProperty("port") + "/" +
                    props.getProperty("databaseName") + "?user=" +
                    props.getProperty("username") + "&password=" +
                    props.getProperty("password");

            conn = DriverManager.getConnection(connString);
        }
        return conn;
    }
}
