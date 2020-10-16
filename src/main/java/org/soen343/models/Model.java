package org.soen343.models;

import org.soen343.connection.DBConnection;
import org.soen343.util.HouseLayoutUtil;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Responsible for creating database connection and handling all the models
 */
public class Model {


    public static House house;
    private static Connection connection;
    public boolean simulationStarted;
    public User user;
    public Time time;

    /**
     * Default constructor for Model object
     */
    public Model() {
        house = HouseLayoutUtil.ReadHouseLayoutFile();
        simulationStarted = false;
        user = new User("test123");
        time = new Time();
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.exit(0);
        }
    }

    public static void updateIndividualRole(String newRole, Integer idSelected) {
        try {
            connection.createStatement().executeUpdate(
                    "UPDATE individuals" +
                            " SET role = '" + newRole +
                            "' WHERE individualId = " + idSelected);
            house.individuals.get(idSelected).setRole(newRole);
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    public void setIndividualsFromUser(String username) {
        try {
            ResultSet rs = connection.createStatement().executeQuery("select * from individuals where username='" + username + "'");

            while (rs.next()) {
                Individual individual = new Individual(rs.getInt("individualId"),
                        rs.getString("name"),
                        rs.getString("role"),
                        "outside",
                        username);
                house.individuals.put(individual.getId(), individual);
            }
            user.setCurrentIndividual((Individual) house.individuals.values().toArray()[0]); //TODO: remove this for proper login logic
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, "Could not retrieve individual list from user", e);
        }
    }

    public void updateIndividualName(String newName, int idSelected) {
        try {
            connection.createStatement().executeUpdate(
                    "update individuals" +
                            " set name = '" + newName +
                            "' where individualId = " + idSelected);

            house.individuals.get(idSelected).setName(newName);
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, "Could not update individual name", e);
        }
    }

    public void addIndividual(String name, String role, String username, String location) {
        int generatedKey = 0;
        try {
            String sqlStatement = "INSERT into individuals" +
                    " (name,role,username) VALUES ('" + name + "','" +
                    role + "','" + username + "')";
            PreparedStatement ps = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }

            Individual ind = new Individual(generatedKey, name, role, location, username);
            house.individuals.put(generatedKey, ind);
            System.out.println("Inserted record's ID : " + generatedKey);
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, "Could not add new Individual", e);
        }
    }

    public void removeIndividual(int idToRemove) {
        try {
            connection.createStatement().executeUpdate("DELETE from individuals" +
                    " where individualId=" + idToRemove);
            house.individuals.remove(idToRemove);
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
