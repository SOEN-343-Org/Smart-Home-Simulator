package org.soen343.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The type Db connection.
 */
public class DBConnection {

    /**
     * Gets connection to MySQL DB.
     *
     * @return Connection connection
     */
    public static Connection getConnection(){

        final String HOSTNAME = "sql9.freemysqlhosting.net";
        final String PORT = "3306";
        final String DB_NAME = "sql9370834";
        final String URL = "jdbc:mysql://" + HOSTNAME + ":" + PORT + "/" + DB_NAME;
        final String USERNAME = "sql9370834";
        final String PASSWORD = "48zb95EinY";

        System.out.println("Connecting database...");

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database connected!");
            return connection;
        }
        catch (SQLException e){
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error connection to the database", e);
            System.exit(0);
        }
        return null;
    }
}
