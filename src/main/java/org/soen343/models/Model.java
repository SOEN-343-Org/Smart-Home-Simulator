package org.soen343.models;

import org.soen343.connection.DBConnection;
import org.soen343.util.HouseLayoutUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Responsible for creating database connection and handling all the models
 */
public class Model {


    public House house;

    public Connection connection;

    /**
     * Default constructor for Model object
     */
    public Model() {
        house = HouseLayoutUtil.ReadHouseLayoutFile();

        try {
            connection = DBConnection.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.exit(0);
        }

    }


}
