package org.soen343.models;

import org.soen343.connection.DBConnection;
import org.soen343.connection.SQLQueriesBuilder;
import org.soen343.util.HouseLayoutUtil;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Responsible for creating database connection and handling all the models
 */
public class Model {


    private static Connection connection;
    public House house;
    public DateTime dateTime;
    public OutsideTemperature outsideTemp;
    public boolean simulationRunning;

    /**
     * Default constructor for Model object
     */
    public Model() {
        house = HouseLayoutUtil.ReadHouseLayoutFile();
        connection = DBConnection.getConnection();
        simulationRunning = false;
        dateTime = new DateTime();
        outsideTemp = new OutsideTemperature();
    }

    public boolean updateIndividualRole(String newRole, Integer idSelected) {
        try {
            String updateUserQuery = SQLQueriesBuilder.updateRole(newRole, idSelected);
            connection.createStatement().executeUpdate(updateUserQuery);
            house.individuals.get(idSelected).setRole(newRole);
            return true;
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }


    public boolean setIndividualsFromUser(String username) {
        try {
            String getIndividualsQuery = SQLQueriesBuilder.getIndividuals(username);
            ResultSet rs = connection.createStatement().executeQuery(getIndividualsQuery);

            while (rs.next()) {
                Individual individual = new Individual(rs.getInt("individualId"),
                        rs.getString("name"),
                        rs.getString("role"),
                        "outside",
                        username);
                house.individuals.put(individual.getId(), individual);
            }

            User.setCurrentIndividual(null);
            return true;

        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, "Could not retrieve individual list from user", e);
        }
        return false;
    }

    public boolean updateIndividualName(String newName, int idSelected) {
        try {
            String updateIndividualNameQuery = SQLQueriesBuilder.updateIndividualName(newName, idSelected);
            connection.createStatement().executeUpdate(updateIndividualNameQuery);
            house.individuals.get(idSelected).setName(newName);
            return true;

        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, "Could not update individual name", e);
        }
        return false;
    }

    public boolean addIndividual(String name, String role, String username, String location) {
        int generatedKey = 0;
        try {
            String addIndividualQuery = SQLQueriesBuilder.addIndividual(name, role, username);
            PreparedStatement ps = connection.prepareStatement(addIndividualQuery, Statement.RETURN_GENERATED_KEYS);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }

           Individual ind = new Individual(generatedKey, name, role, location, username);
            house.individuals.put(generatedKey, ind);
            System.out.println("Inserted record's ID : " + generatedKey);
            return true;

        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, "Could not add new Individual", e);
        }
        return false;
    }

    public boolean removeIndividual(int idToRemove) {
        try {
            String removeIndividualQuery = SQLQueriesBuilder.removeIndividual(idToRemove);
            connection.createStatement().executeUpdate(removeIndividualQuery);
            house.individuals.remove(idToRemove);
            return true;

        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean setSimulationIsRunning() {
        Individual ind = User.getCurrentIndividual();
        if (ind == null) {
            System.out.println("Cannot start simulation, profile not selected");
            return false;
        }
        simulationRunning = !simulationRunning;
        return true;
    }
}
