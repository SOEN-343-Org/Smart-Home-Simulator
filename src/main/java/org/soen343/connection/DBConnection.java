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
     * @throws SQLException the sql exception
     */
    public static Connection getConnection(){

        String hostName = "sql9.freemysqlhosting.net";
        String port = "3306";
        String dbName = "sql9370834";
        String url = "jdbc:mysql://" + hostName + ":" + port + "/" + dbName;
        String username = "sql9370834";
        String password = "48zb95EinY";

        System.out.println("Connecting database...");

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
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
