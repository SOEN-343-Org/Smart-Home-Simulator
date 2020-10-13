package org.soen343.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {

    public  static Connection getConnection()  throws SQLException {

        String url = "jdbc:mysql://localhost:3306/";
        String dbName="smarthomesimulator";
        String username="root";
        String password="";

        System.out.println("Connecting database...");

        Connection connection= DriverManager.getConnection(url+dbName,username,password);

        System.out.println("Database connected!");

        return connection;
    }
}
